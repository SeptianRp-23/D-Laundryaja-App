package com.android.dlaundryaja.Activity.PageUser.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageUser.Account.AccountActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.DashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.History.HistoryActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StatusActivity extends AppCompatActivity {

    private static final String TAG = StatusActivity.class.getSimpleName() ;
    ImageView imgHistory, imgStatus;
    TextView pesanan;
    private String GetUserAPI = Api.URL_API + "getPesananUser.php";
    String getId;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        pesanan = findViewById(R.id.status);
        imgHistory = findViewById(R.id.story);
        imgStatus = findViewById(R.id.log2);

        if (pesanan.getText().equals("Belum ada Pemesanan")){
            imgStatus.setBackgroundResource(R.drawable.ic_sad);
        }

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatusActivity.this, HistoryActivity.class));
            }
        });
//
//        final String Status = pesanan.getText().toString();
//        if (Status.("Belum ada Pemesanan")){
////            imgStatus.setBackgroundResource(R.drawable.progress);
//            imgStatus.setBackgroundResource(R.drawable.ic_progress);
//        }
//        if (Status.contentEquals("Menunggu Konfirmasi")){
//            imgStatus.setBackgroundResource(R.drawable.ic_menunggu);
//        }
//        if (Status.equals("Di Konfirmasi")){
//            imgStatus.setBackgroundResource(R.drawable.ic_dikonfirmasi);
//        }


        //BottomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
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
//                        startActivity(new Intent(getApplicationContext(),
//                                StatusActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),
                                AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//        //end BottomNav
    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Intent intent = new Intent(StatusActivity.this, HistoryActivity.class);
//        startActivity(intent);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.adduser:
//                Intent intent = new Intent(StatusActivity.this, TambahUserActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.account:
//                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(i);
//                return true;
//            case R.id.logout:
//                Toast.makeText(this, "Terimakasih", Toast.LENGTH_SHORT).show();
//                logout();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void getUserDetail(){
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

                                    pesanan.setText(strStatus);

                                    if (strStatus.equals("")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_progress);
                                    }else if (strStatus.equals("Menunggu Konfirmasi")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_menunggu);
                                    }else if (strStatus.equals("Di Konfirmasi")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_dikonfirmasi);
                                    }else if (strStatus.equals("Di Jemput")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_dijemput);
                                    }else if (strStatus.equals("Di Proses")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_diproses);
                                    }else if (strStatus.equals("Di Antar")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_diantar);
                                    }else if (strStatus.equals("Di Terima")){
                                        imgStatus.setBackgroundResource(R.drawable.ic_diterima);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StatusActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StatusActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", getId);
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
    }
}