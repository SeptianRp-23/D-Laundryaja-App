package com.android.dlaundryaja.Activity.PageUser.Status.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.AdmPesananTrackActivity;
import com.android.dlaundryaja.Activity.PageUser.Account.UserAccountActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserDashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.UserStatusActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Utils.Adapter.DataItem.DataPesanan;
import com.android.dlaundryaja.Utils.Adapter.TrackingAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserHistoryActivity extends AppCompatActivity {

    private ImageView imgBack;
    String getID;
    ListView listTrack;
    AdapterHistory adapterHistory;
    public static ArrayList<ItemHistory> itemHistoryArrayList = new ArrayList<>();
    private String DataPesananApi = Api.URL_API + "userGetHistory.php";
    ItemHistory itemHistory;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    LinearLayout anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        listTrack = findViewById(R.id.list_history);
        adapterHistory = new AdapterHistory(this, itemHistoryArrayList);
        listTrack.setAdapter(adapterHistory);
        anim = findViewById(R.id.animText);

        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHistoryActivity.this, UserStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        receiveData();

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

    public void receiveData() {
        StringRequest request = new StringRequest(Request.Method.POST, DataPesananApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        itemHistoryArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String invoice = object.getString("invoice");
                                    String id_user = object.getString("id_user");
                                    String jenis = object.getString("jenis");
                                    String tanggal = object.getString("tanggal");
                                    String status = object.getString("status");

                                    if (jsonArray.length() == 0) {
//                                        anim.setVisibility(View.VISIBLE);
                                        Toast.makeText(UserHistoryActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        itemHistory = new ItemHistory(invoice, id_user, jenis, tanggal, status);
                                        anim.setVisibility(View.GONE);
                                        itemHistoryArrayList.add(itemHistory);
                                        adapterHistory.notifyDataSetChanged();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserHistoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}