package com.eos.parcelnoticemanager.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.eos.parcelnoticemanager.R;

public class ParcelDetailDialog extends Dialog {
    Button btnConfirm, btnCancel;
    EditText etSender, etReceiver;
    String sender;
    private static String receiver;

    public ParcelDetailDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_detail_dialog);
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

    public static void setReceiver(String receiver) {
        ParcelDetailDialog.receiver = receiver;
    }
}