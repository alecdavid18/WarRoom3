package com.example.tournaments.warroom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

public class UserAuthenticationOTP extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String mVerificationCode;
    EditText phnum;
    String pnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_otp_auth);
        phnum=(EditText)findViewById(R.id.ph_num);
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);

            }


            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mResendToken = token;
                mVerificationCode=verificationId;
            }
        };
        Button phnum=(Button)findViewById(R.id.phnum_enter);
        phnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyNumber();

            }
        });
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phnum.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this,"Invalid phone number.", Toast.LENGTH_LONG).show();
            //mPhoneNumberField.setTextColor(Color.parseColor("#ff1744"));
            return false;
        }

        return true;
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    void verifyNumber()
    {

        if (!validatePhoneNumber()) {
            return;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

        }
        else
        {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }

        final Dialog dialog = new Dialog(UserAuthenticationOTP.this);
        dialog.setContentView(R.layout.otp_dialogbox);
        dialog.show();
        Button enter_otp=(Button)dialog.findViewById(R.id.enter_otp);
        enter_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtpView otpView;
                otpView = findViewById(R.id.otp_view);
                String get_otp= otpView.getOTP();
                verifyPhoneNumberWithCode(mVerificationCode,get_otp);
            }
        });
        ///////hide keyboard start
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        /////////hide keyboard end


        //mStatusText.setText("Authenticating....!");

        startPhoneNumberVerification("+91"+phnum.getText().toString());
        pnum="+91"+phnum.getText().toString();
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final boolean[] userfound = {false};

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //////////
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
                            SharedPreferences.Editor uidpref=pref.edit();
                            uidpref.putString("phone_num", pnum);

                            uidpref.commit();

                            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();

                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            Log.e("AUTH","inside taskcomplete");

                            if(isNew!=true)
                            {
                                Log.e("AUTH","inside not isnew");

                                usersRef.child("PHONE_ID").child(pnum).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        Log.e("AUTH","inside ondatachange");
                                        if(dataSnapshot.getValue()!=null)
                                        {
                                            Log.e("AUTH","inside ds!=null"+dataSnapshot);
                                            String uid=dataSnapshot.getValue().toString();
                                            SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE);
                                            SharedPreferences.Editor uidpref=pref.edit();
                                            uidpref.putString("uid", uid);

                                            uidpref.commit();
                                            startActivity(new Intent(UserAuthenticationOTP.this,CenterActivity.class));
                                        }
                                        else{
                                            toRegisterNewUser();
                                            Log.e("AUTH","inside setnewnode");
                                            startActivity(new Intent(UserAuthenticationOTP.this, RegisterNewUser.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("AUTH","inside oncancelled "+databaseError);

                                    }
                                });
                            }
                            else
                            {
                                toRegisterNewUser();
                                Log.e("AUTH","inside outer else setnewnode");
                                startActivity(new Intent(UserAuthenticationOTP.this, RegisterNewUser.class));
                            }
                            Log.e("AUTH","going inside intent");


                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                            }
                        }
                    }
                });

    }
    void toRegisterNewUser()
    {
        startActivity(new Intent(this,RegisterNewUser.class));
    }

}
