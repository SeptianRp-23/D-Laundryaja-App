package com.android.dlaundryaja.Activity.PageAdmin.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.Laporan.AdmLaporanActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk.AdmPesananMasukActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananProses.AdmPesananProsesActivivty;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.AdmPesananTrackActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.Profile.AdmProfileActivity;
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

public class AdmMenuDashboard extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    private static final String TAG = AdmMenuDashboard.class.getSimpleName() ;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getId, getNama;
    TextView txtNama, txtIsiArray1, txtIsiArray2;
    ImageView alertjemput, alertantar;
    private String GetNotifMasukAPI = Api.URL_API + "admGetNotif1.php";
    private String GetNotifProsesAPI = Api.URL_API + "admGetNotif2.php";
    Handler mHandler;
    CardView cNew, cProses, cSelesai, cLaporan;
    Button bt_Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_menu_dashboard);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        getNama = user.get(SessionManager.NAME);
        alertjemput = findViewById(R.id.img_alert1);
        alertantar = findViewById(R.id.img_alert2);
        cNew = findViewById(R.id.card_pesanan_baru);
        cProses = findViewById(R.id.card_pesanan_proses);
        cSelesai = findViewById(R.id.card_tugas_selesai);
        cLaporan = findViewById(R.id.card_laporan);
        bt_Logout = findViewById(R.id.btLogout);
        txtIsiArray1 = findViewById(R.id.isi_array1);
        txtIsiArray2 = findViewById(R.id.isi_array2);

        bt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        cNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmMenuDashboard.this, AdmPesananMasukActivity.class));
            }
        });

        cProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmMenuDashboard.this, AdmPesananProsesActivivty.class));
            }
        });

        cSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmMenuDashboard.this, AdmPesananTrackActivity.class));
            }
        });

        cLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmMenuDashboard.this, AdmLaporanActivity.class));
            }
        });

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,2000);
    }
    private final Runnable m_Runnable = new Runnable(){
        public void run() {
            AdmMenuDashboard.this.mHandler.postDelayed(m_Runnable, 5000);
            getNotifMasuk();
            getNotifProses();
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

    private void getNotifMasuk(){
        alertjemput.setVisibility(View.GONE);
        txtIsiArray1.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetNotifMasukAPI,

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
                                    String arrlength = String.valueOf(jsonArray.length());
                                    txtIsiArray1.setText(arrlength);

                                    if (strStatus.equals("Menunggu Konfirmasi")){
                                        alertjemput.setVisibility(View.VISIBLE);
                                        txtIsiArray1.setVisibility(View.VISIBLE);
                                        Toast.makeText(AdmMenuDashboard.this, "Check Alert Menu!", Toast.LENGTH_SHORT).show();
//                                        finish();
                                    }else{
                                        txtIsiArray1.setVisibility(View.GONE);
                                        alertjemput.setVisibility(View.GONE);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdmMenuDashboard.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmMenuDashboard.this, "Error Connection", Toast.LENGTH_SHORT).show();
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

    private void getNotifProses(){
        alertantar.setVisibility(View.GONE);
        txtIsiArray2.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetNotifProsesAPI,

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
                                    String arrlength = String.valueOf(jsonArray.length());
                                    txtIsiArray2.setText(arrlength);

                                    if (strStatus.equals("Di Proses")){
                                        alertantar.setVisibility(View.VISIBLE);
                                        txtIsiArray2.setVisibility(View.VISIBLE);
                                        Toast.makeText(AdmMenuDashboard.this, "Check Alert Menu!", Toast.LENGTH_SHORT).show();
//                                        finish();
                                    }else{
                                        alertantar.setVisibility(View.GONE);
                                        txtIsiArray2.setVisibility(View.GONE);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdmMenuDashboard.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmMenuDashboard.this, "Error Connection", Toast.LENGTH_SHORT).show();
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
        getNotifMasuk();
        getNotifProses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adm_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(AdmMenuDashboard.this, AdmProfileActivity.class));
        return super.onOptionsItemSelected(item);
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