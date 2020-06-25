package com.android.dlaundryaja.Activity.PageKurir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.dlaundryaja.Activity.PageKurir.Akun.KurirAkunActivity;
import com.android.dlaundryaja.Activity.PageKurir.DiJemput.KurirJemputActivity;
import com.android.dlaundryaja.Activity.PageKurir.Diantar.KurirAntarActivity;
import com.android.dlaundryaja.Activity.PageKurir.Tugas.KurirTugasActivity;
import com.android.dlaundryaja.Login.LoginActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class KurirDashboardActivity extends AppCompatActivity {

    private static final String TAG = KurirDashboardActivity.class.getSimpleName() ;
    SessionManager sessionManager;
    String getId, getNama;
    TextView txtNama;
    ImageView alertjemput, alertantar;
    private String GetUserAPI = Api.URL_API + "getStatus.php";
    Handler mHandler;
    CardView cJemput, cAntar, cTugas, cProfil;
    SharedPreferences sharedPreferences;
    Button bt_Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_dashboard);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        getNama = user.get(SessionManager.NAME);
        txtNama = findViewById(R.id.txt_nama_kurir);
        alertjemput = findViewById(R.id.img_alert1);
        alertantar = findViewById(R.id.img_alert2);
        cJemput = findViewById(R.id.card_jemput);
        cAntar = findViewById(R.id.card_antar);
        cTugas = findViewById(R.id.card_tugas);
        cProfil = findViewById(R.id.card_profil);
        bt_Logout = findViewById(R.id.btLogout);

        bt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        cJemput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KurirDashboardActivity.this, KurirJemputActivity.class));
            }
        });

        cAntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KurirDashboardActivity.this, KurirAntarActivity.class));
            }
        });

        cTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KurirDashboardActivity.this, KurirTugasActivity.class));
            }
        });

        cProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KurirDashboardActivity.this, KurirAkunActivity.class));
            }
        });

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,2000);
        txtNama.setText(getNama);
    }
    private final Runnable m_Runnable = new Runnable(){
        public void run() {
            KurirDashboardActivity.this.mHandler.postDelayed(m_Runnable, 5000);
            getUserDetail();
//            progressDialog = new ProgressDialog(KurirDashboardActivity.this);
//            progressDialog.setMessage("Please Wait..."); // Setting Message
//            progressDialog.setTitle("Sync Activity"); // Setting Title
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//             //1800000
//            progressDialog.show(); // Display Progress Dialog
//            progressDialog.setCancelable(false);
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    progressDialog.dismiss();
//                }
//            }).start();
//            Toast.makeText(KurirDashboardActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
        }

    };//runnable
//
//
    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
//        finish();
//        onPause();
    }

    private void getUserDetail(){
        alertjemput.setVisibility(View.GONE);
        alertantar.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetUserAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strHarga = object.getString("harga").trim();
                                    String strKet = object.getString("keterangan").trim();
                                    String strStatus = object.getString("status").trim();

                                    if (strStatus.equals("Di Jemput")){
                                        alertjemput.setVisibility(View.VISIBLE);
                                        Toast.makeText(KurirDashboardActivity.this, "Check Menu Jemput !", Toast.LENGTH_SHORT).show();
//                                        finish();
                                    }else if (strStatus.equals("Di Antar")){
                                        alertantar.setVisibility(View.VISIBLE);
                                        Toast.makeText(KurirDashboardActivity.this, "Check Menu Antar !", Toast.LENGTH_SHORT).show();
//                                        finish();
                                    }else{
                                        alertantar.setVisibility(View.GONE);
                                        alertjemput.setVisibility(View.GONE);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KurirDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KurirDashboardActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                    }
                })
        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("invoice", getId);
//                return params;
//            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    private void Logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
        editor.apply();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}