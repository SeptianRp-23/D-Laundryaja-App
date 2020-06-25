package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.Controler.ItemData;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.Controler.TrackingAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdmPesananTrackDetail extends AppCompatActivity {

//    private String DataPesananApi = Api.URL_API + "getTracking.php";
    private String DataPesananApi = Api.URL_API + "getTracking.php";
    TextView etInvoice, etJenis;
    int position;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    String getID, getInv;
    ListView listTrack;
    ImageView btback;
    TrackingAdapter trackingAdapterp;
    public static ArrayList<ItemData> arrayItemData = new ArrayList<>();
    ItemData itemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_track_detail);


        receiveData();
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        //set Data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        etInvoice = findViewById(R.id.txt_invoice);
        etJenis = findViewById(R.id.jenis_pesanan);

        etInvoice.setText(AdmPesananTrackActivity.dataPesananArrayList.get(position).getInvoice());
        etJenis.setText(AdmPesananTrackActivity.dataPesananArrayList.get(position).getJenis());

        getInv = etInvoice.getText().toString().trim();
        listTrack = findViewById(R.id.myListTrackingDtl);

        trackingAdapterp = new TrackingAdapter(this, arrayItemData);
        listTrack.setAdapter(trackingAdapterp);

        btback = findViewById(R.id.back);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayItemData.clear();
                startActivity(new Intent(AdmPesananTrackDetail.this, AdmPesananTrackActivity.class));
            }
        });
    }

    public void receiveData(){
//        final String getInv = etInvoice.getText().toString().trim();
//        final String getdata = getInv;
        StringRequest request = new StringRequest(Request.Method.POST, DataPesananApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayItemData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String invoice = object.getString("invoice");
                                    String jenis = object.getString("jenis");
                                    String tanggal = object.getString("tanggal");
                                    String id_user = object.getString("id_user");
                                    String nama_user = object.getString("nama_user");
                                    String level = object.getString("level");
                                    String status = object.getString("status");

//                                    String arrlength = String.valueOf(jsonArray.length());

                                        itemData = new ItemData(invoice, jenis, tanggal, id_user, nama_user, level, status);
                                        arrayItemData.add(itemData);
                                        trackingAdapterp.notifyDataSetChanged();
//                                    Toast.makeText(AdmPesananTrackDetail.this, "Berhasil", Toast.LENGTH_SHORT).show();


                                }
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(AdmPesananTrackDetail.this, "Maaf Sedang Bermasalah", Toast.LENGTH_SHORT).show();
                            System.out.println(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmPesananTrackDetail.this, "Maaf Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("invoice", getInv);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        arrayItemData.clear();
        super.onBackPressed();
    }
}