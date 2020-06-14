package com.android.dlaundryaja.Activity.PageAdmin.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Local.Api;
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

import java.util.HashMap;
import java.util.Map;

public class AdmDetailDashboard extends AppCompatActivity {

    MaterialEditText etInvoice, etIdUser, etJenis, etTanggal, etNama, etTelp, etAlamat, etDetail, etStatus;
    int position;
    Button btKonfirm;
    private String StatusAPI = Api.URL_API + "editStatus.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_detail_dashboard);

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
        btKonfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdmDetailDashboard.this, "Pressed", Toast.LENGTH_SHORT).show();
                SaveEditDetail();
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
        etInvoice.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getInvoice());
        etIdUser.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getId_user());
        etJenis.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getJenis());
        etTanggal.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getTanggal());
        etNama.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getNama());
        etTelp.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getTelp());
        etAlamat.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getAlamat());
        etDetail.setText(AdmDashboardActivity.dataPesananArrayList.get(position).getDetail());
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
        progressDialog.setMessage("Saving...");
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
                                Toast.makeText(AdmDetailDashboard.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdmDetailDashboard.this, AdmDashboardActivity.class);
                                startActivity(intent);
//                                sessionManager.createSession(email, name, id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AdmDetailDashboard.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AdmDetailDashboard.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();

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
}