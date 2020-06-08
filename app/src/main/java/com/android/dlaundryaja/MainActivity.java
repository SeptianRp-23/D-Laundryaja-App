package com.android.dlaundryaja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.dlaundryaja.Login.LoginActivity;
import com.android.dlaundryaja.Utils.Controller.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String getID;
    SharedPreferences sharedPreferences;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        logout = findViewById(R.id.bt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

    }

    private void Logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
        editor.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}