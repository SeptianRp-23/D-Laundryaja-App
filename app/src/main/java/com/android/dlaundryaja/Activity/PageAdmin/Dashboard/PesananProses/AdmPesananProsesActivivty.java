package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananProses;

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
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk.AdmPesananMasukActivity;
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

public class AdmPesananProsesActivivty extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    String getID, getNama;
    ListView listProsesAdm;
    PesananAdapter pesananAdapter;
    public static ArrayList<DataPesanan> dataPesananArrayList = new ArrayList<>();
    private String PesananProses = Api.URL_API + "admGet_diProses.php";
    private String StatusUpdate = Api.URL_API + "editStatus.php";
    private String InsertTracking = Api.URL_API + "insertTracking.php";
    DataPesanan dataPesanan;
    TextView tvSts, txtKosong, txtLevel, txtIdUser, txtNamaUser;
    ImageView btBack, imgKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_pesanan_proses);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.NAME);

        listProsesAdm = findViewById(R.id.myListAdmProses);
        pesananAdapter = new PesananAdapter(this, dataPesananArrayList);
        listProsesAdm.setAdapter(pesananAdapter);
        tvSts = findViewById(R.id.txtSts);
        btBack = findViewById(R.id.back);
        imgKosong = findViewById(R.id.img_tired);
        txtKosong = findViewById(R.id.txtKosong);
        txtLevel = findViewById(R.id.txt_set_level);
        txtIdUser = findViewById(R.id.txt_set_id);
        txtNamaUser = findViewById(R.id.txt_set_nama);

        txtIdUser.setText(getID);
        txtNamaUser.setText(getNama);

        receiveData();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmPesananProsesActivivty.this, AdmMenuDashboard.class));
            }
        });

        listProsesAdm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                SaveEditDetail();
                startActivity(new Intent(getApplicationContext(), AdmMenuDashboard.class)
                        .putExtra("position", position));

                final ProgressDialog progressDialog = new ProgressDialog(AdmPesananProsesActivivty.this);
                progressDialog.setMessage("Saving...");
                progressDialog.show();
                final String Tstatus = tvSts.getText().toString().trim();
                final String Tinvoice = dataPesananArrayList.get(position).getInvoice();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, StatusUpdate,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")){
                                        Toast.makeText(AdmPesananProsesActivivty.this, "Success!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(AdmDashboardActivity.this, AdmDashboardActivity.class);
//                                startActivity(intent);
//                                sessionManager.createSession(email, name, id);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    Toast.makeText(AdmPesananProsesActivivty.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(AdmPesananProsesActivivty.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("status", Tstatus);
                        params.put("invoice", Tinvoice);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AdmPesananProsesActivivty.this);
                requestQueue.add(stringRequest);

                //insert
                final String Tvinvoice = dataPesananArrayList.get(position).getInvoice();
                final String TvJenis = dataPesananArrayList.get(position).getJenis();
                final String TvTanggal = dataPesananArrayList.get(position).getTanggal();
                final String TvID = txtIdUser.getText().toString().trim();
                final String TvNama = txtNamaUser.getText().toString().trim();
                final String TvLevel = txtLevel.getText().toString().trim();
                final String TvStatus = tvSts.getText().toString().trim();

                StringRequest request = new StringRequest(Request.Method.POST, InsertTracking,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("success")) {
                                    Toast.makeText(AdmPesananProsesActivivty.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AdmPesananProsesActivivty.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdmPesananProsesActivivty.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("invoice", Tvinvoice);
                        params.put("jenis", TvJenis);
                        params.put("tanggal", TvTanggal);
                        params.put("id_user", TvID);
                        params.put("nama_user", TvNama);
                        params.put("level", TvLevel);
                        params.put("status", TvStatus);
                        return params;
                    }
                };
                RequestQueue rqQ = Volley.newRequestQueue(AdmPesananProsesActivivty.this);
                rqQ.add(request);
            }
        });
    }

    public void receiveData(){
        StringRequest request = new StringRequest(Request.Method.POST, PesananProses,
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

                                    if (status.equals("Di Proses")){
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
                        Toast.makeText(AdmPesananProsesActivivty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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