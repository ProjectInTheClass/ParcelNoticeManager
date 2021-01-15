package com.eos.parcelnoticemanager.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.retrofit.DormitoryApi;
import com.eos.parcelnoticemanager.retrofit.ParcelApi;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelDetailDialog extends Dialog {
    Button btnConfirm, btnCancel;
    EditText etSender, etReceiver;
    String sender;
    private static String receiverName;
    private static int receiverId;
    private static int roomId;
    private static int dormitoryId;
    private ParcelApi parcelApi;
    Context context;

    public ParcelDetailDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_detail_dialog);
        init();
        btnConfirm = findViewById(R.id.parcel_detail_dialog_ok_button);
        btnCancel = findViewById(R.id.parcel_detail_dialog_cancel_button);
        etSender = findViewById(R.id.parcel_detail_dialog_sender_editText);
        etReceiver = findViewById(R.id.parcel_detail_dialog_receiver_editText);

        if(receiverName!=null){
            etReceiver.setText(receiverName);
            etReceiver.setEnabled(false);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender = etSender.getText().toString();
                //택배 등록
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("sender",sender);
                jsonObject.addProperty("status","보관중");
                jsonObject.addProperty("recipient",receiverId);
                jsonObject.addProperty("room",roomId);
                jsonObject.addProperty("dormitory",dormitoryId);
                Call<String> callGetResponse = parcelApi.add_parcel(ParcelRegisterActivity.getToken(),jsonObject);
                callGetResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context,response.body(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                dismiss();
            }
        }) ;
        btnCancel.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void init() {
        parcelApi = new Retrofit.Builder()
                .baseUrl(ParcelRegisterActivity.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParcelApi.class);
    }

    public static void setReceiver(int receiverId,String receiverName) {
        ParcelDetailDialog.receiverId = receiverId;
        ParcelDetailDialog.receiverName = receiverName;
    }
    public static void setRoomId(int room){roomId = room;}
    public static void setDormitory(int dormitory) {dormitoryId = dormitory;}
}