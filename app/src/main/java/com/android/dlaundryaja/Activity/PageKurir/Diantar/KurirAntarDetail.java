package com.android.dlaundryaja.Activity.PageKurir.Diantar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageKurir.DiJemput.KurirJemputDetail;
import com.android.dlaundryaja.Activity.PageKurir.KurirDashboardActivity;
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

public class KurirAntarDetail extends AppCompatActivity {

    MaterialEditText et_inv, et_id, et_nama, et_telp, et_alamat, et_lokasi, et_detail;
    TextView txtIdKurir, txtNamaKurir, txtStatus, txtLevel, et_status, et_tglSkrng, txt_tgl_bln, txt_jenis;
    Button btKirim;
    String getID, getNama;
    SessionManager sessionManager;
    String myFormat = "dd-MM-yyy hh:mm a";
    String myBulan = "yyyy-MM";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    SimpleDateFormat sdf2 = new SimpleDateFormat(myBulan, Locale.US);
    private String InsertTugas = Api.URL_API + "insertTugas.php";
    private String EditStatus = Api.URL_API + "editStatus.php";
    private String InsertTracking = Api.URL_API + "insertTracking.php";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_antar_detail);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.NAME);

        et_inv = findViewById(R.id.txt_inv_antr);
        et_id = findViewById(R.id.txt_id_antr);
        et_nama = findViewById(R.id.txt_nama_antr);
        et_telp = findViewById(R.id.txt_telp_antr);
        et_alamat = findViewById(R.id.txt_alamat_antr);
        et_lokasi = findViewById(R.id.txt_lokasi_antr);
        et_detail = findViewById(R.id.txt_detail_antr);
        et_status = findViewById(R.id.txt_status_antr);
        et_tglSkrng = findViewById(R.id.txt_tglSkrg_antr);
        txtIdKurir = findViewById(R.id.txt_id_kurir);
        txtNamaKurir = findViewById(R.id.txt_nama_kurir);
        txtLevel = findViewById(R.id.txt_level_kurir);
        txtStatus = findViewById(R.id.txt_status_tugas_antr);
        btKirim = findViewById(R.id.kirim_antr);
        txt_tgl_bln = findViewById(R.id.txt_tglbln_antr);
        txt_jenis = findViewById(R.id.txt_jenis_pesanan);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        et_tglSkrng.setText(str1);
        Calendar c2 = Calendar.getInstance();
        String str2 = sdf2.format(c2.getTime());
        txt_tgl_bln.setText(str2);

        txtIdKurir.setText(getID);
        txtNamaKurir.setText(getNama);

        btKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
                InsertTracking();
                SaveEditDetail();
                startActivity(new Intent(KurirAntarDetail.this, KurirDashboardActivity.class));
            }
        });

        //set Data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        et_inv.setText(KurirAntarActivity.dataPesananArrayList.get(position).getInvoice());
        et_id.setText(KurirAntarActivity.dataPesananArrayList.get(position).getId_user());
        et_nama.setText(KurirAntarActivity.dataPesananArrayList.get(position).getNama());
        et_telp.setText(KurirAntarActivity.dataPesananArrayList.get(position).getTelp());
        et_alamat.setText(KurirAntarActivity.dataPesananArrayList.get(position).getAlamat());
        et_lokasi.setText(KurirAntarActivity.dataPesananArrayList.get(position).getLokasi());
        et_detail.setText(KurirAntarActivity.dataPesananArrayList.get(position).getDetail());
        txt_jenis.setText(KurirAntarActivity.dataPesananArrayList.get(position).getJenis());


        //Disable ET
        et_inv.setEnabled(false);
        et_id.setEnabled(false);
        et_nama.setEnabled(false);
        et_telp.setEnabled(false);
        et_alamat.setEnabled(false);
        et_lokasi.setEnabled(false);
        et_detail.setEnabled(false);

    }

    private void InsertData() {
        final String txtInvoice = et_inv.getText().toString().trim();
        final String txtkurir = txtIdKurir.getText().toString().trim();
        final String txtnama = txtNamaKurir.getText().toString().trim();
        final String txtTgl = et_tglSkrng.getText().toString().trim();
        final String txtStat = txtStatus.getText().toString().trim();
        final String txtBulan = txt_tgl_bln.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, InsertTugas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            Toast.makeText(KurirAntarDetail.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KurirAntarDetail.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KurirAntarDetail.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("perbulan", txtBulan);
                params.put("id_transaksi", txtInvoice);
                params.put("id_kurir", txtkurir);
                params.put("nama", txtnama);
                params.put("tanggal", txtTgl);
                params.put("status", txtStat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KurirAntarDetail.this);
        requestQueue.add(request);
    }

    private void SaveEditDetail() {

//        final String merk = this.etMerk.getText().toString().trim();
//        final String nama = this.etNama.getText().toString().trim();
//        final String warna = this.etWarna.getText().toString().trim();
//        final String plat = this.etPlat.getText().toString().trim();
//        final String tahun = this.etTahun.getText().toString().trim();
        final String status = this.et_status.getText().toString().trim();
        final String invoice = this.et_inv.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EditStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
//                                Toast.makeText(KurirJemputDetail.this, "Success!", Toast.LENGTH_SHORT).show();
//                                sessionManager.createSession(email, name, id);
                                System.out.println("Berhasil");
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
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
        final String txtInvoice = et_inv.getText().toString().trim();
        final String txtJenis = txt_jenis.getText().toString().trim();
        final String txtTgl = et_tglSkrng.getText().toString().trim();
        final String txtStatusProses = et_status.getText().toString().trim();
        final String txtID = txtIdKurir.getText().toString().trim();
        final String txtNama = txtNamaKurir.getText().toString().trim();
        final String txtLvl = txtLevel.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, InsertTracking,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            System.out.println("Berhasil");
                        } else {
                            System.out.println("Gagal");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
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
                params.put("level", txtLvl);
                params.put("status", txtStatusProses);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KurirAntarDetail.this);
        requestQueue.add(request);
    }

}