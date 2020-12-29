package com.eos.parcelnoticemanager.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.eos.parcelnoticemanager.R;
import com.eos.parcelnoticemanager.retrofit.ParcelApi;
import com.eos.parcelnoticemanager.tools.ParcelRegisterActivity;
import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelDetailDialog extends Dialog {
    Button btnConfirm, btnCancel;
    EditText etSender, etReceiver;
    String sender;
    private static String receiver;
    private static int receiverId;
    private static int roomId;
    private static int dormitoryId;
    private ParcelApi parcelApi;

    public ParcelDetailDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_detail_dialog);
        initRetrofit();
        btnConfirm = findViewById(R.id.parcel_detail_dialog_ok_button);
        btnCancel = findViewById(R.id.parcel_detail_dialog_cancel_button);
        etSender = findViewById(R.id.parcel_detail_dialog_sender_editText);
        etReceiver = findViewById(R.id.parcel_detail_dialog_receiver_editText);

        if(receiver!=null){
            etReceiver.setText(receiver);
            etReceiver.setEnabled(false);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender = etSender.getText().toString();
                System.out.println("snd: "+sender + " , rcv: "+receiver);
                //택배 등록
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("sender",sender);
                jsonObject.addProperty("status","보관중");
                jsonObject.addProperty("recipient",receiverId);
                jsonObject.addProperty("room",roomId);
                jsonObject.addProperty("dormitory",dormitoryId);
                parcelApi.add_parcel(ParcelRegisterActivity.getToken(),jsonObject);
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

    private void initRetrofit() {
        parcelApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParcelApi.class);
    }

    private String getString(int base_url) {
        return getString(base_url);
    }

    public static void setReceiver(int receiverId, String receiver) {
        ParcelDetailDialog.receiverId = receiverId;
        ParcelDetailDialog.receiver = receiver;
    }
    public static void setRoomId(int room){roomId = room;}
    public static void setDormitory(int dormitory) {dormitoryId = dormitory;}
}