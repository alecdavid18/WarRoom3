package com.example.tournaments.warroom;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class MainActivity extends AppCompatActivity {

    
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final AppCompatActivity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
       pay.put("send_sms", true);
      pay.put("send_email", true);
 } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }
    
    InstapayListener listener;

    
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Call the function callInstamojo to start payment here
        Button payy=(Button)findViewById(R.id.payy);
        payy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInstamojoPay("pranavmadavadev78@gmail.com", "9447806723","20", "Simply", "Pranav");
            }
        });
    }
}
