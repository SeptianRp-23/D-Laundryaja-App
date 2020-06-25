package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.AdmPesananTrackActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.dlaundryaja.Utils.Adapter.DataItem.DataPesanan;
import com.android.dlaundryaja.Utils.Adapter.PesananAdapter;
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

public class AdmPesananMasukActivity extends AppCompatActivity {


    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    String getID;
    ListView listViewAdm;
    PesananAdapter pesananAdapter;
    public static ArrayList<DataPesanan> dataPesananArrayList = new ArrayList<>();
    private String DataPesananApi = Api.URL_API + "dataPesanan.php";
    private String InsertTrack = Api.URL_API + "insertTracking.php";
    DataPesanan dataPesanan;
    TextView tvSts, tvLvl, txtKosong;
    ImageView btBack, imgKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_pesanan_masuk);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        listViewAdm = findViewById(R.id.myListviewAdm);
        pesananAdapter = new PesananAdapter(this, dataPesananArrayList);
        listViewAdm.setAdapter(pesananAdapter);
        tvSts = findViewById(R.id.txtSts);
        tvLvl = findViewById(R.id.txtLvl);
        btBack = findViewById(R.id.back);
        imgKosong = findViewById(R.id.img_tired);
        txtKosong = findViewById(R.id.txtKosong);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmPesananMasukActivity.this, AdmMenuDashboard.class));
            }
        });

        receiveData();

        listViewAdm.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                SaveEditDetail();
                startActivity(new Intent(getApplicationContext(), AdmPesananMasukDetail.class)
                        .putExtra("position", position));

//                final String status = this.tvSts.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(AdmPesananMasukActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                final String Tinvoice = dataPesananArrayList.get(position).getInvoice();
                final String TidPemesan = dataPesananArrayList.get(position).getId_user();
                final String TnamaPemesan = dataPesananArrayList.get(position).getNama();
                final String TJenis = dataPesananArrayList.get(position).getJenis();
                final String Ttanggal = dataPesananArrayList.get(position).getTanggal();
                final String Tlevel = tvLvl.getText().toString().trim();
                final String Tstatus = tvSts.getText().toString().trim();

                StringRequest request = new StringRequest(Request.Method.POST, InsertTrack,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("success")) {
                                    Toast.makeText(AdmPesananMasukActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(AdmPesananMasukDetail.this, UserDashboardActivity.class);
//                            startActivity(intent);
                                } else {
                                    Toast.makeText(AdmPesananMasukActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdmPesananMasukActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("invoice", Tinvoice);
                        params.put("jenis", TJenis);
                        params.put("tanggal", Ttanggal);
                        params.put("id_user", TidPemesan);
                        params.put("nama_user", TnamaPemesan);
                        params.put("level", Tlevel);
                        params.put("status", Tstatus);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AdmPesananMasukActivity.this);
                requestQueue.add(request);
            }
//                progressDialog.dismiss();
//                startActivity(new Intent(getApplicationContext(), DetailMobilActivity.class)
////                        .putExtra("position", position));
//                CharSequence[] dialogItem = {"View Data", "Delete Data"};
//                builder.setTitle(dataPesananArrayList.get(position).getNama());
//                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        switch (i) {
//                            case 0:
//
////                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                break;
//                            case 1:
//                                deleteData(dataItemMobilArrayList.get(position).getId());
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();

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
                                for (int i = 0; i < jsonArray.length(); i++){
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

                                    if (status.equals("Menunggu Konfirmasi")){
                                        dataPesanan = new DataPesanan(invoice, id_user, jenis, tanggal, nama, telp, alamat, lokasi, detail, harga, keterangan, status);
                                        dataPesananArrayList.add(dataPesanan);
                                        pesananAdapter.notifyDataSetChanged();
                                        imgKosong.setVisibility(View.GONE);
                                        txtKosong.setVisibility(View.GONE);
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
                        Toast.makeText(AdmPesananMasukActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
}