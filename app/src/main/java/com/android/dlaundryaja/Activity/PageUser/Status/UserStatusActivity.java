package com.android.dlaundryaja.Activity.PageUser.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageUser.Account.UserAccountActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserDashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.History.UserHistoryActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Test.BottomSheetDialog.BottomSheetLogin;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserStatusActivity extends AppCompatActivity {

    private static final String TAG = UserStatusActivity.class.getSimpleName() ;
    ImageView imgHistory, imgStatus;
    public TextView pesanan;
    private String GetUserAPI = Api.URL_API + "getPesananUser.php";
    String getId;
    LinearLayout story;
    CardView cardTrack;
    SessionManager sessionManager;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        pesanan = findViewById(R.id.status);
        imgStatus = findViewById(R.id.log2);
        cardTrack = findViewById(R.id.card_track);

        cardTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                bottomSheetLogin.show(getSupportFragmentManager(), "TAG");
            }
        });

        story = findViewById(R.id.bt_history);
        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserStatusActivity.this, UserHistoryActivity.class));
                overridePendingTransition(0,0);
            }
        });

        if (pesanan.getText().equals("Belum ada Pemesanan")){
            imgStatus.setBackgroundResource(R.drawable.ic_sad);
        }

//        imgHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserStatusActivity.this, UserHistoryActivity.class));
//            }
//        });
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
                                UserDashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
//                        startActivity(new Intent(getApplicationContext(),
//                                StatusActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),
                                UserAccountActivity.class));
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
                            Toast.makeText(UserStatusActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserStatusActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
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