package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserDashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserPemesananActivity;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdmPesananMasukDetail extends AppCompatActivity {

    MaterialEditText etInvoice, etIdUser, etJenis, etTanggal, etNama, etTelp, etAlamat, etDetail, etStatus, etTglSkrng;
    int position;
    Button btKonfirm;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getId, getNama, getLevel;
    TextView SetId, SetNama, SetLevel;
    String myFormat = "dd-MM-yyy hh:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private String StatusAPI = Api.URL_API + "editStatus.php";
    private String InsertTracking = Api.URL_API + "insertTracking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_pesanan_masuk_detail);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();

        etInvoice = findViewById(R.id.txt_inv_dtl);
        etJenis = findViewById(R.id.txt_jenis_dtl);
        etIdUser = findViewById(R.id.txt_id_dtl);
        etTanggal = findViewById(R.id.txt_tgl_dtl);
        etNama = findViewById(R.id.txt_nama_dtl);
        etTelp = findViewById(R.id.txt_telp_dtl);
        etAlamat = findViewById(R.id.txt_alamat_dtl);
        etDetail = findViewById(R.id.txt_detail_dtl);
        etStatus = findViewById(R.id.txt_status_dtl);
        btKonfirm = findViewById(R.id.konfirmasi);
        SetId = findViewById(R.id.txt_set_id);
        SetNama = findViewById(R.id.txt_set_nama);
        SetLevel = findViewById(R.id.txt_set_level);
        etTglSkrng = findViewById(R.id.txt_tglSkrg_dtl);

        getId = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.NAME);

        SetId.setText(getId);
        SetNama.setText(getNama);

        btKonfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdmPesananMasukDetail.this, "Pressed", Toast.LENGTH_SHORT).show();
                SaveEditDetail();
                InsertTracking();
            }
        });

        //disable
        etInvoice.setEnabled(false);
        etIdUser.setEnabled(false);
        etJenis.setEnabled(false);
        etTanggal.setEnabled(false);
        etNama.setEnabled(false);
        etTelp.setEnabled(false);
        etAlamat.setEnabled(false);
        etDetail.setEnabled(false);

        //set Data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        etInvoice.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getInvoice());
        etIdUser.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getId_user());
        etJenis.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getJenis());
        etTanggal.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getTanggal());
        etNama.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getNama());
        etTelp.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getTelp());
        etAlamat.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getAlamat());
        etDetail.setText(AdmPesananMasukActivity.dataPesananArrayList.get(position).getDetail());

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        etTglSkrng.setText(str1);
    }

    private void SaveEditDetail() {

//        final String merk = this.etMerk.getText().toString().trim();
//        final String nama = this.etNama.getText().toString().trim();
//        final String warna = this.etWarna.getText().toString().trim();
//        final String plat = this.etPlat.getText().toString().trim();
//        final String tahun = this.etTahun.getText().toString().trim();
        final String status = this.etStatus.getText().toString().trim();
        final String invoice = this.etInvoice.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, StatusAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(AdmPesananMasukDetail.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdmPesananMasukDetail.this, AdmMenuDashboard.class);
                                startActivity(intent);
//                                sessionManager.createSession(email, name, id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AdmPesananMasukDetail.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AdmPesananMasukDetail.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", status);
                params.put("invoice", invoice);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void InsertTracking() {
        final String txtInvoice = etInvoice.getText().toString().trim();
        final String txtJenis = etJenis.getText().toString().trim();
        final String txtTgl = etTglSkrng.getText().toString().trim();
        final String txtStatus = etStatus.getText().toString().trim();
        final String txtID = SetId.getText().toString().trim();
        final String txtNama = SetNama.getText().toString().trim();
        final String txtLevel = SetLevel.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, InsertTracking,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            Toast.makeText(AdmPesananMasukDetail.this, "Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(AdmPesananMasukDetail.this, UserDashboardActivity.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(AdmPesananMasukDetail.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmPesananMasukDetail.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("invoice", txtInvoice);
                params.put("jenis", txtJenis);
                params.put("tanggal", txtTgl);
                params.put("id_user", txtID);
                params.put("nama_user", txtNama);
                params.put("level", txtLevel);
                params.put("status", txtStatus);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AdmPesananMasukDetail.this);
        requestQueue.add(request);
    }
}