package com.example.tournaments.warroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PurchaseEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_activity);

        NumberPicker entry_num=(NumberPicker)findViewById(R.id.number_picker);
        entry_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView amount=(TextView)findViewById(R.id.amount_to_pay);
                amount.setText("₹"+newVal*50);
            }
        });
        Button pay_amount=(Button)findViewById(R.id.continue_pay);
        pay_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PurchaseEntryActivity.this,"Congrats..you have purchased",Toast.LENGTH_LONG).show();
            }
        });
    }
}
