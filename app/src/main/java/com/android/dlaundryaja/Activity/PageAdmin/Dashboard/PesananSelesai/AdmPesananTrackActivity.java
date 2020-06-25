package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk.AdmPesananMasukActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk.AdmPesananMasukDetail;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Utils.Adapter.DataItem.DataPesanan;
import com.android.dlaundryaja.Utils.Adapter.PesananAdapter;
import com.android.dlaundryaja.Utils.Adapter.TrackingAdapter;
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

public class AdmPesananTrackActivity extends AppCompatActivity {

    String getID;
    ListView listTrack;
    TrackingAdapter trackingAdapter;
    public static ArrayList<DataPesanan> dataPesananArrayList = new ArrayList<>();
    private String DataPesananApi = Api.URL_API + "admGet_Status.php";
    DataPesanan dataPesanan;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    ImageView btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_track);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        listTrack = findViewById(R.id.myListAdmTrack);
        trackingAdapter = new TrackingAdapter(this, dataPesananArrayList);
        listTrack.setAdapter(trackingAdapter);
        btBack = findViewById(R.id.back);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmPesananTrackActivity.this, AdmMenuDashboard.class));
            }
        });

        receiveData();

        listTrack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                SaveEditDetail();
                startActivity(new Intent(getApplicationContext(), AdmPesananTrackDetail.class)
                        .putExtra("position", position));

////                final String status = this.tvSts.getText().toString().trim();
//                final ProgressDialog progressDialog = new ProgressDialog(AdmPesananTrackActivity.this);
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.show();
            }
        });
    }

    public void receiveData(){
        StringRequest request = new StringRequest(Request.Method.POST, DataPesananApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataPesananArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String invoice = object.getString("invoice");
                                    String id_user = object.getString("id_user");
                                    String jenis = object.getString("jenis");
                                    String tanggal = object.getString("tanggal");
                                    String nama = object.getString("nama");
                                    String telp = object.getString("telp");
                                    String alamat = object.getString("alamat");
                                    String lokasi = object.getString("lokasi");
                                    String detail = object.getString("detail");
                                    String harga = object.getString("harga");
                                    String keterangan = object.getString("keterangan");
                                    String status = object.getString("status");

                                    if (jsonArray.length() == 0) {
                                        Toast.makeText(AdmPesananTrackActivity.this, "Maaf Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dataPesanan = new DataPesanan(invoice, id_user, jenis, tanggal, nama, telp, alamat, lokasi, detail, harga, keterangan, status);
                                        dataPesananArrayList.add(dataPesanan);
                                        trackingAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmPesananTrackActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        requestQueue.add(request);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdmPesananTrackActivity.this, AdmMenuDashboard.class));
    }
}