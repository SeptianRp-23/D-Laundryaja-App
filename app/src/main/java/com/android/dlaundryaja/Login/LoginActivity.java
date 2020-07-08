package com.android.dlaundryaja.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.Activity.PageKurir.KurirDashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserDashboardActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Register.RegisterActivity;
import com.android.dlaundryaja.Server.Local.Api;
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

public class LoginActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    Button btLogin, btRegist;
    MaterialEditText Mail, Pass;
    CheckBox loginstate;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    private String LoginAPI = Api.URL_API + "loginUser.php";
//    public static String URL_LOGIN_USER = "http://192.168.0.124//api/kkp_project/user/loginUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        btRegist = findViewById(R.id.register);
        btLogin = findViewById(R.id.log_btn_login);
        Mail = findViewById(R.id.log_txt_email);
        Pass = findViewById(R.id.log_txt_pass);
        loginstate = findViewById(R.id.state);

        btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtemail = Mail.getText().toString().trim();
                String txtpass = Pass.getText().toString().trim();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpass)){
                    Toast.makeText(LoginActivity.this, "field can not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    btLogin.setBackgroundResource(R.drawable.circle_block);
                    btLogin.setEnabled(false);
                    loginProces(txtemail, txtpass);
                }
            }
        });
        String loginstatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState),"");
        if (loginstatus.equals("LoggedIn")){
            startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
        }
        else if (loginstatus.equals("LoggedOn")){
            startActivity(new Intent(LoginActivity.this, AdmMenuDashboard.class));
        }
        else if (loginstatus.equals("LoggedEn")){
            startActivity(new Intent(LoginActivity.this, KurirDashboardActivity.class));
        }
    }

//    private void showDialog() {
////        final AlertDialog.Builder alert;
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
////            alert = new AlertDialog.Builder(this);
////        }
////        else{
////            alert = new AlertDialog.Builder(this);
////        }
////        LayoutInflater inflater = getLayoutInflater();
////        View view = inflater.inflate(R.layout.custom_alert_sucess, null);
////
////        alert.setView(view);
////        alert.setCancelable(false);
////
////        final AlertDialog dialog = alert.create();
////        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////        dialog.show();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//
//            }
//        },3000);
//
//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    private void loginProces(final String email, final String password) {
        final AlertDialog.Builder alert;
        final Handler handler = new Handler();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alert = new AlertDialog.Builder(this);
        } else {
            alert = new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_alert_sucess, null);
        alert.setView(view);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginAPI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                                    if (success.equals("1")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String id = object.getString("id").trim();
                                            String name = object.getString("name").trim();
                                            String email = object.getString("email").trim();
                                            String tgl = object.getString("tgl").trim();
                                            String telp = object.getString("telp").trim();
                                            String alamat = object.getString("alamat").trim();
                                            String level = object.getString("level").trim();

                                            SharedPreferences.Editor editor = sharedPreferences.edit();

//                                            if (loginstate.isChecked()) {
//                                                editor.putString(getResources().getString(R.string.prefLoginState), "LoggedIn");
//                                            } else {
//                                                editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
//                                            }
//                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            if (loginstate.isChecked() && level.equals("user")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedIn");
                                            }else if (loginstate.isChecked() && level.equals("admin")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOn");
                                            }else if (loginstate.isChecked() && level.equals("kurir")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedEn");
                                            }
                                            else {
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }

                                            if (level.equals("user")){
                                                sessionManager.createSession(id, name, email, tgl, telp, alamat,  level);
                                                editor.apply();
                                                final Intent inte = new Intent(LoginActivity.this, UserDashboardActivity.class);
                                                inte.putExtra("name", name);
                                                inte.putExtra("email", email);
                                                dialog.show();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(inte);
                                                        finish();
                                                    }

                                                }, 3000);

                                            }

                                            else if (level.equals("admin")){
                                                sessionManager.createSession(id, name, email, tgl, telp, alamat,  level);
                                                editor.apply();
                                                final Intent it = new Intent(LoginActivity.this, AdmMenuDashboard.class);
                                                it.putExtra("name", name);
                                                it.putExtra("email", email);
                                                dialog.show();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(it);
                                                        finish();
                                                    }

                                                }, 3000);
                                            }

                                            else if (level.equals("kurir")){
                                                sessionManager.createSession(id, name, email, tgl, telp, alamat,  level);
                                                editor.apply();
                                                final Intent in = new Intent(LoginActivity.this, KurirDashboardActivity.class);
                                                in.putExtra("name", name);
                                                in.putExtra("email", email);
                                                dialog.show();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(in);
                                                        finish();
                                                    }

                                                }, 3000);
                                            }
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    btLogin.setBackgroundResource(R.drawable.circle_primary);
                                    btLogin.setEnabled(true);
                                    Toast.makeText(LoginActivity.this, "Error, Email Or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Error, Check Connection" + error.toString(), Toast.LENGTH_SHORT).show();
                                btLogin.setBackgroundResource(R.drawable.circle_primary);
                                btLogin.setEnabled(true);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
            }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}