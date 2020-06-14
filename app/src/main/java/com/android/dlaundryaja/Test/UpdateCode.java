package com.android.dlaundryaja.Test;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateCode {
//    private void SaveEditDetail() {
//
//        final String name = this.name.getText().toString().trim();
//        final String email = this.email.getText().toString().trim();
//        final String telepon = this.telepon.getText().toString().trim();
//        final String alamat = this.alamat.getText().toString().trim();
//        final String id = getId;
//        final MediaPlayer mp_simpan = MediaPlayer.create(this, R.raw.data_disimpan);
//        final MediaPlayer mp_gagal = MediaPlayer.create(this, R.raw.data_blm_lengkap);
//        final MediaPlayer mp_error = MediaPlayer.create(this, R.raw.koneksi_error);
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Saving...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateUserAPI,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            if (success.equals("1")){
//                                mp_simpan.start();
//                                Toast.makeText(ProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                                getUserDetail();
//                                disabled();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            mp_error.start();
//                            progressDialog.dismiss();
//                            Toast.makeText(ProfileActivity.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        mp_error.start();
//                        Toast.makeText(ProfileActivity.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name);
//                params.put("email", email);
//                params.put("telepon", telepon);
//                params.put("alamat", alamat);
//                params.put("id", id);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
}
