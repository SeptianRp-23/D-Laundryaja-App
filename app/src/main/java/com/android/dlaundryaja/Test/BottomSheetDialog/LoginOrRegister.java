package com.android.dlaundryaja.Test.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Rest.Api;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginOrRegister extends AppCompatActivity {

    Button btnLogin, btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        btnLogin = findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                bottomSheetLogin.show(getSupportFragmentManager(), "TAG");
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetRegist bottomSheetRegist = new BottomSheetRegist();
                bottomSheetRegist.show(getSupportFragmentManager(), "TAG");
            }
        });
    }
}