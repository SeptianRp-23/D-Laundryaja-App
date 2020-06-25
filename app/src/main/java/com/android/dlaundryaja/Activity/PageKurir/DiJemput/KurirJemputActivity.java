package com.android.dlaundryaja.Activity.PageKurir.DiJemput;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class KurirJemputActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private String getID;
    private ListView listKurirJmpt;
    PesananAdapter pesananAdapter;
    public static ArrayList<DataPesanan> dataPesananArrayList = new ArrayList<>();
    private String DataPesananApi = Api.URL_API + "getStatusJemput.php";
    DataPesanan dataPesanan;
    TextView tv_Sts, tv_kosong;
    Handler mHandler;
    ImageView imgKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_jemput);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        receiveData();

        listKurirJmpt = findViewById(R.id.myListviewKurirJmpt);
        pesananAdapter = new PesananAdapter(this, dataPesananArrayList);
        listKurirJmpt.setAdapter(pesananAdapter);
        tv_Sts = findViewById(R.id.txtSts);
        tv_kosong = findViewById(R.id.txtKosong);
        imgKosong = findViewById(R.id.img_tired);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,5000);

        listKurirJmpt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                SaveEditDetail();
                startActivity(new Intent(getApplicationContext(), KurirJemputDetail.class)
                        .putExtra("position", position));
            }
        });
    }
    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            KurirJemputActivity.this.mHandler.postDelayed(m_Runnable, 15000);
            receiveData();
        }
    };

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

                                    if (status.equals("Di Jemput")){
                                        dataPesanan = new DataPesanan(invoice, id_user, jenis, tanggal, nama, telp, alamat, lokasi, detail, harga, keterangan, status);
                                        dataPesananArrayList.add(dataPesanan);
                                        pesananAdapter.notifyDataSetChanged();
                                        tv_kosong.setVisibility(View.GONE);
                                        imgKosong.setVisibility(View.GONE);
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
                        Toast.makeText(KurirJemputActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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