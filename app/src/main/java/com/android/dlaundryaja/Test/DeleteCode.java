package com.android.dlaundryaja.Test;

import android.media.MediaPlayer;
import android.widget.Toast;

import com.android.dlaundryaja.MainActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DeleteCode {
//    private void deleteData(final String id){
//        final MediaPlayer mp_dihapus = MediaPlayer.create(this, R.raw.data_dihapus);
//        final MediaPlayer mp_error = MediaPlayer.create(this, R.raw.koneksi_error);
//        StringRequest request = new StringRequest(Request.Method.POST, DeleteAPI,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.equalsIgnoreCase("sucess")){
//                            Toast.makeText(MainActivity.this, "Sucess!, Data Dihapus", Toast.LENGTH_SHORT).show();
//                            mp_dihapus.start();
//                            receiveData();
//                        }
//                        else {
//                            mp_error.start();
//                            Toast.makeText(MainActivity.this, "Error!, Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        mp_error.start();
//                        Toast.makeText(MainActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
//                        System.out.println(error);
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", id);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(request);
//    }
}
