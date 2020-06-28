package com.android.dlaundryaja.Activity.PageKurir.DiJemput;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananMasuk.AdmPesananMasukDetail;
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

public class KurirJemputDetail extends AppCompatActivity {

    MaterialEditText etId, etJenis, etTanggal,etTelp, etLokasi, etAlamat, etDetail, etHarga, etKet, etberat;
    TextView txtIdKurir, txtStatus, txtNama, tglSkrg, tvHarga, txtgetNama, txtBulanSkrg, txtLvl, txtStatusTugas;
    Button btKirim;
    CardView cardView;
    ImageView imgShow, imgHide;
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
        setContentView(R.layout.activity_kurir_jemput_detail);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.NAME);

        etId = findViewById(R.id.txt_inv_dtl);
        etJenis = findViewById(R.id.txt_jenis_dtl);
        etTanggal = findViewById(R.id.txt_tanggal_dtl);
        etTelp = findViewById(R.id.txt_telp_dtl);
        etLokasi = findViewById(R.id.txt_lokasi_dtl);
        etDetail = findViewById(R.id.txt_detail_dtl);
        etHarga = findViewById(R.id.txt_harga_dtl);
        etAlamat = findViewById(R.id.txt_alamat_dtl);
        etKet = findViewById(R.id.txt_ket_dtl);
        txtIdKurir = findViewById(R.id.id_kurir);
        txtStatus = findViewById(R.id.id_status);
        txtNama = findViewById(R.id.id_nama);
        tglSkrg = findViewById(R.id.tanggal_skrng);
        txtBulanSkrg = findViewById(R.id.bulan_skrng);
        tvHarga = findViewById(R.id.txtHarga);
        etberat = findViewById(R.id.txt_berat);
        txtgetNama = findViewById(R.id.txt_get_nama);
        etKet.setOnEditorActionListener(editorActionListener);
        imgShow = findViewById(R.id.show);
        imgHide = findViewById(R.id.hide);
        cardView = findViewById(R.id.card1);
        txtLvl = findViewById(R.id.txt_set_level);
        txtStatusTugas = findViewById(R.id.txt_status_tugas);

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgHide.setVisibility(View.VISIBLE);
                imgShow.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
            }
        });

        imgHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgHide.setVisibility(View.GONE);
                imgShow.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.GONE);
            }
        });

//        final String etBerat = etKet.getText().toString().trim();
//        if (etBerat != ""){
//
//        }

        btKirim = findViewById(R.id.konfirmasi);

        btKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(KurirJemputDetail.this, "Pressed", Toast.LENGTH_SHORT).show();
                InsertData();
                SaveEditDetail();
                InsertTracking();
                Intent intent = new Intent(KurirJemputDetail.this, KurirDashboardActivity.class);
                startActivity(intent);
            }
        });

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        tglSkrg.setText(str1);
        Calendar c2 = Calendar.getInstance();
        String str2 = sdf2.format(c2.getTime());
        txtBulanSkrg.setText(str2);

        txtIdKurir.setText(getID);
        txtNama.setText(getNama);


        //set Data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        etId.setText(KurirJemputActivity.dataPesananArrayList.get(position).getInvoice());
        etJenis.setText(KurirJemputActivity.dataPesananArrayList.get(position).getJenis());
        etTanggal.setText(KurirJemputActivity.dataPesananArrayList.get(position).getTanggal());
        etTelp.setText(KurirJemputActivity.dataPesananArrayList.get(position).getTelp());
        etLokasi.setText(KurirJemputActivity.dataPesananArrayList.get(position).getLokasi());
        etAlamat.setText(KurirJemputActivity.dataPesananArrayList.get(position).getAlamat());
        etDetail.setText(KurirJemputActivity.dataPesananArrayList.get(position).getDetail());
        tvHarga.setText(KurirJemputActivity.dataPesananArrayList.get(position).getHarga());
        txtgetNama.setText(KurirJemputActivity.dataPesananArrayList.get(position).getNama());

        //Disable ET
        etId.setEnabled(false);
        etJenis.setEnabled(false);
        etTanggal.setEnabled(false);
        etTelp.setEnabled(false);
        etLokasi.setEnabled(false);
        etAlamat.setEnabled(false);
        etDetail.setEnabled(false);
        etHarga.setEnabled(false);
        etberat.setEnabled(false);
        etKet.requestFocus();

    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i){
                case EditorInfo.IME_ACTION_SEND:
                    Hitung();
                    btKirim.setVisibility(View.VISIBLE);
            }
            return false;
        }
    };

    private void Hitung(){
        //convert string ke int
        String Sharga = tvHarga.getText().toString();
        int Iharga = Integer.parseInt(Sharga);

        String Sjum = etKet.getText().toString();
        int Ijum = Integer.parseInt(Sjum);

        int x = Iharga * Ijum;
        String total = String.valueOf(x);
        etHarga.setText(total);
    }

    private void InsertData() {
        final String txtInvoice = etId.getText().toString().trim();
        final String txtkurir = txtIdKurir.getText().toString().trim();
        final String txtnama = txtNama.getText().toString().trim();
        final String txtTgl = tglSkrg.getText().toString().trim();
        final String txtStat = txtStatusTugas.getText().toString().trim();
        final String txtBulan = txtBulanSkrg.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, InsertTugas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            Toast.makeText(KurirJemputDetail.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KurirJemputDetail.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KurirJemputDetail.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(KurirJemputDetail.this);
        requestQueue.add(request);
    }

    private void SaveEditDetail() {

//        final String merk = this.etMerk.getText().toString().trim();
//        final String nama = this.etNama.getText().toString().trim();
//        final String warna = this.etWarna.getText().toString().trim();
//        final String plat = this.etPlat.getText().toString().trim();
//        final String tahun = this.etTahun.getText().toString().trim();
        final String status = this.txtStatus.getText().toString().trim();
        final String harga = this.etHarga.getText().toString().trim();
        final String invoice = this.etId.getText().toString().trim();

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
                params.put("harga", harga);
                params.put("invoice", invoice);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void InsertTracking() {
        final String txtInvoice = etId.getText().toString().trim();
        final String txtJenis = etJenis.getText().toString().trim();
        final String txtTgl = tglSkrg.getText().toString().trim();
        final String txtStatusProses = txtStatus.getText().toString().trim();
        final String txtID = txtIdKurir.getText().toString().trim();
        final String txtNamaKurir = txtNama.getText().toString().trim();
        final String txtLevel = txtLvl.getText().toString().trim();

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
                params.put("nama_user", txtNamaKurir);
                params.put("level", txtLevel);
                params.put("status", txtStatusProses);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KurirJemputDetail.this);
        requestQueue.add(request);
    }
}