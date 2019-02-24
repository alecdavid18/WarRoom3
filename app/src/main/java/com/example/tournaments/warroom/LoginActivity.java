package com.example.tournaments.warroom;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sigin=(Button)findViewById(R.id.SiginButton);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
        String uid=pref.getString("uid","");
        if(uid.equals(""))
            startActivity(new Intent(LoginActivity.this,UserAuthenticationOTP.class));
        else
            startActivity(new Intent(LoginActivity.this,CenterActivity.class));

    }
}
