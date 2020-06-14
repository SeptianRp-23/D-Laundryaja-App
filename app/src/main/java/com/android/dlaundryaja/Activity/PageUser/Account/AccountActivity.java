package com.android.dlaundryaja.Activity.PageUser.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageUser.Dashboard.DashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.StatusActivity;
import com.android.dlaundryaja.Login.LoginActivity;
import com.android.dlaundryaja.MainActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = StatusActivity.class.getSimpleName() ;
    SessionManager sessionManager;
    String getID;
    SharedPreferences sharedPreferences;
    Button btLogout, btUpdate;
    ImageView btEdit;
    MaterialEditText etID, etNama, etEmail, etTgl, etTelp, etAlamat;
    private String GetUserAPI = Api.URL_API + "dataUser.php";
    private String EditUser = Api.URL_API + "editUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        btEdit = findViewById(R.id.editakun);
        btLogout = findViewById(R.id.exitakun);
        btUpdate = findViewById(R.id.update);
        etID = findViewById(R.id.txt_id);
        etNama = findViewById(R.id.txt_nama);
        etEmail = findViewById(R.id.txt_email);
        etTgl = findViewById(R.id.txt_tgl);
        etTelp = findViewById(R.id.txt_telp);
        etAlamat = findViewById(R.id.txt_alamat);

        disabled();

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNama.setEnabled(true);
                etEmail.setEnabled(true);
                etTgl.setEnabled(true);
                etTelp.setEnabled(true);
                etAlamat.setEnabled(true);
                btLogout.setVisibility(View.GONE);
                btUpdate.setVisibility(View.VISIBLE);
                btEdit.setVisibility(View.GONE);
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEditDetail();
            }
        });


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setMessage("Confirm Exit");
                builder.setIcon(R.drawable.ic_exit);
                builder.setMessage("Yakin Ingin Keluar ?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yaa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Logout();
//                        finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AccountActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        //BottomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),
                                StatusActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
//                        startActivity(new Intent(getApplicationContext(),
//                                AccountActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//        //end BottomNav
    }
    private void disabled(){
        etID.setEnabled(false);
        etNama.setEnabled(false);
        etEmail.setEnabled(false);
        etTgl.setEnabled(false);
        etTelp.setEnabled(false);
        etAlamat.setEnabled(false);
        btUpdate.setVisibility(View.GONE);
        btLogout.setVisibility(View.VISIBLE);
        btEdit.setVisibility(View.VISIBLE);
    }

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetUserAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strId = object.getString("id").trim();
                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strTgl = object.getString("tgl").trim();
                                    String strTelepon = object.getString("telp").trim();
                                    String strAlamat = object.getString("alamat").trim();

                                    etID.setText(strId);
                                    etNama.setText(strName);
                                    etEmail.setText(strEmail);
                                    etTgl.setText(strTgl);
                                    etTelp.setText(strTelepon);
                                    etAlamat.setText(strAlamat);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AccountActivity.this, "Error Reading Detail "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AccountActivity.this, "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
        disabled();
    }

    private void SaveEditDetail() {

        final String txtName = this.etNama.getText().toString().trim();
        final String txtEmail = this.etEmail.getText().toString().trim();
        final String txtTgl = this.etTgl.getText().toString().trim();
        final String txtTelp = this.etTelp.getText().toString().trim();
        final String txtAlamat = this.etAlamat.getText().toString().trim();
        final String id = getID;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EditUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(AccountActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                                sessionManager.createSession(email, name, id);
                                disabled();
                                getUserDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AccountActivity.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AccountActivity.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", txtName);
                params.put("email", txtEmail);
                params.put("tgl", txtTgl);
                params.put("telp", txtTelp);
                params.put("alamat", txtAlamat);
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
        editor.apply();
        startActivity(new Intent(AccountActivity.this, LoginActivity.class));
        finish();
    }
}