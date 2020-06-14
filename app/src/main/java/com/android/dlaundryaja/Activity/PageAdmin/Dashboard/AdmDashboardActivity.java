package com.android.dlaundryaja.Activity.PageAdmin.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Akun.AdmAkunActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Laporan.AdmLaporanActivity;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdmDashboardActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private long backPressedTime;
    private Toast backToast;

    SessionManager sessionManager;
    String getID;
    ListView listViewAdm;
    PesananAdapter pesananAdapter;
    public static ArrayList<DataPesanan> dataPesananArrayList = new ArrayList<>();
    private String DataPesananApi = Api.URL_API + "dataPesanan.php";
    DataPesanan dataPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_dashboard);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        listViewAdm = findViewById(R.id.myListviewAdm);
        pesananAdapter = new PesananAdapter(this, dataPesananArrayList);
        listViewAdm.setAdapter(pesananAdapter);

        receiveData();

        listViewAdm.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), AdmDetailDashboard.class)
                        .putExtra("position", position));
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
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.adm_dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.adm_dashboard:
//                        startActivity(new Intent(getApplicationContext(),
//                                TransaksiActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.report:
                        startActivity(new Intent(getApplicationContext(),
                                AdmLaporanActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.adm_account:
                        startActivity(new Intent(getApplicationContext(),
                                AdmAkunActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
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

                                    dataPesanan = new DataPesanan(invoice, id_user, jenis, tanggal, nama, telp, alamat, lokasi, detail, harga, keterangan, status);
                                    dataPesananArrayList.add(dataPesanan);
                                    pesananAdapter.notifyDataSetChanged();
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
                        Toast.makeText(AdmDashboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}