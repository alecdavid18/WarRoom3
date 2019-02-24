package com.example.tournaments.warroom;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_register);
        Button registerButton=(Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String f_name=(((EditText)findViewById(R.id.nameEditText)).getText()).toString();
                final String g_name=(((EditText)findViewById(R.id.gameName)).getText()).toString();
                if(f_name.equals("")||g_name.equals("")) {
                    Toast.makeText(RegisterNewUser.this,"Complete the names",Toast.LENGTH_LONG).show();
                    return;
                }
                final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();
                usersRef.child("num_users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
                        String pnum=pref.getString("phone_num","");

                        int value=Integer.parseInt(dataSnapshot.getValue().toString());
                        usersRef.child("User_details").child((value+1)+"").child("game_name").setValue(g_name);
                        usersRef.child("User_details").child((value+1)+"").child("name").setValue(f_name);
                        usersRef.child("num_users").setValue((value+1)+"");
                        usersRef.child("PHONE_ID").child(pnum).setValue((value+1)+"");
                        SharedPreferences.Editor uidpref=pref.edit();
                        uidpref.putString("game_name", g_name);
                        uidpref.putString("full_name", f_name);
                        uidpref.commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
}}
