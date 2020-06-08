package com.android.dlaundryaja.Test.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.dlaundryaja.R;

public class HomePage extends AppCompatActivity {

    TextView txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        txtEmail = findViewById(R.id.itemEmail);
        Intent intent = getIntent();
        if (intent.getExtras() != null){
            String itemEmail = intent.getStringExtra("data");
            txtEmail.setText(itemEmail);
        }
    }
}