package com.android.dlaundryaja.Test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.android.dlaundryaja.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertCode {
//    private void InsertData() {
//        final String txtMerk = etBrand.getText().toString().trim();
//        final String txtNama = etNama.getText().toString().trim();
//        final String txtPlat = etPlat.getText().toString().trim();
//        final String txtTahun = etTahun.getText().toString().trim();
//        final String txtWarna = etWarna.getText().toString().trim();
//        final String txtHarga = etHarga.getText().toString().trim();
//        final String txtStatus = tvstatus.getText().toString().trim();
//        final String txtTgl = tvtgl.getText().toString().trim();
//        final MediaPlayer mp_simpan = MediaPlayer.create(this, R.raw.data_disimpan);
//        final MediaPlayer mp_gagal = MediaPlayer.create(this, R.raw.data_blm_lengkap);
//        final MediaPlayer mp_error = MediaPlayer.create(this, R.raw.koneksi_error);
//
//        final ProgressDialog progressDialog = new ProgressDialog(TambahMobilActivity.this);
//        progressDialog.setMessage("Loading . . .");
//
//        if (txtMerk.isEmpty() || txtNama.isEmpty() || txtPlat.isEmpty() || txtTahun.isEmpty() || txtWarna.isEmpty() || txtHarga.isEmpty()) {
//            Toast.makeText(TambahMobilActivity.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
//            mp_gagal.start();
//            return;
//        } else {
//            progressDialog.show();
//            StringRequest request = new StringRequest(Request.Method.POST, AddMobilAPI,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equalsIgnoreCase("success")) {
//                                Toast.makeText(TambahMobilActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                                mp_simpan.start();
//                                progressDialog.dismiss();
//                                Intent intent = new Intent(TambahMobilActivity.this, MainActivity.class);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(TambahMobilActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                                mp_error.start();
//                                progressDialog.dismiss();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(TambahMobilActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            mp_error.start();
//                            progressDialog.dismiss();
//                        }
//                    }
//            ){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("merk", txtMerk);
//                    params.put("nama", txtNama);
//                    params.put("plat", txtPlat);
//                    params.put("tahun", txtTahun);
//                    params.put("warna", txtWarna);
//                    params.put("harga", txtHarga);
//                    params.put("status", txtStatus);
//                    params.put("tgl", txtTgl);
//                    return params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(TambahMobilActivity.this);
//            requestQueue.add(request);
//        }
}
