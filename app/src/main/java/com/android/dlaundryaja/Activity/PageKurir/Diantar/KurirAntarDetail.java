package com.android.dlaundryaja.Activity.PageKurir.Diantar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.dlaundryaja.R;

public class KurirAntarDetail extends AppCompatActivity {

//    MaterialEditText etId, etJenis, etTanggal, etTelp, etLokasi, etAlamat, etDetail, etHarga, etKet, etberat;
//    TextView txtIdKurir, txtStatus, txtNama, tglSkrg, tvHarga;
//    Button btKirim;
//    String getID, getNama;
//    SessionManager sessionManager;
//    String myFormat = "dd-MM-yyy hh:mm a";
//    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//    private String InsertTugas = Api.URL_API + "insertTugas.php";
//    private String EditStatus = Api.URL_API + "editStatus.php";
//    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_antar_detail);

//        sessionManager = new SessionManager(this);
//        HashMap<String, String> user = sessionManager.getUserDetail();
//        getID = user.get(SessionManager.ID);
//        getNama = user.get(SessionManager.NAME);
//
//        etId = findViewById(R.id.txt_inv_dtl);
//        etJenis = findViewById(R.id.txt_jenis_dtl);
//        etTanggal = findViewById(R.id.txt_tanggal_dtl);
//        etTelp = findViewById(R.id.txt_telp_dtl);
//        etLokasi = findViewById(R.id.txt_lokasi_dtl);
//        etDetail = findViewById(R.id.txt_detail_dtl);
//        etHarga = findViewById(R.id.txt_harga_dtl);
//        etAlamat = findViewById(R.id.txt_alamat_dtl);
//        etKet = findViewById(R.id.txt_ket_dtl);
//        txtIdKurir = findViewById(R.id.id_kurir);
//        txtStatus = findViewById(R.id.id_status);
//        txtNama = findViewById(R.id.id_nama);
//        tglSkrg = findViewById(R.id.tanggal_skrng);
//        tvHarga = findViewById(R.id.txtHarga);
//        etberat = findViewById(R.id.txt_berat);
//        etKet.setOnEditorActionListener(editorActionListener);
//
//        btKirim = findViewById(R.id.konfirmasi);
//
//        btKirim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(KurirAntarDetail.this, "Pressed", Toast.LENGTH_SHORT).show();
//                InsertData();
//                SaveEditDetail();
//            }
//        });
//
//        //Set Tanggal
//        Calendar c1 = Calendar.getInstance();
//        String str1 = sdf.format(c1.getTime());
//        tglSkrg.setText(str1);
//
//        txtIdKurir.setText(getID);
//        txtNama.setText(getNama);
//
//
//        //set Data
//        Intent intent = getIntent();
//        position = intent.getExtras().getInt("position");
//        etId.setText(KurirAntarActivity.dataPesananArrayList.get(position).getInvoice());
//        etJenis.setText(KurirAntarActivity.dataPesananArrayList.get(position).getJenis());
//        etTanggal.setText(KurirAntarActivity.dataPesananArrayList.get(position).getTanggal());
//        etTelp.setText(KurirAntarActivity.dataPesananArrayList.get(position).getTelp());
//        etLokasi.setText(KurirAntarActivity.dataPesananArrayList.get(position).getLokasi());
//        etAlamat.setText(KurirAntarActivity.dataPesananArrayList.get(position).getAlamat());
//        etDetail.setText(KurirAntarActivity.dataPesananArrayList.get(position).getDetail());
//        tvHarga.setText(KurirAntarActivity.dataPesananArrayList.get(position).getHarga());
//
//
//        //Disable ET
//        etId.setEnabled(false);
//        etJenis.setEnabled(false);
//        etTanggal.setEnabled(false);
//        etTelp.setEnabled(false);
//        etLokasi.setEnabled(false);
//        etAlamat.setEnabled(false);
//        etDetail.setEnabled(false);
//        etHarga.setEnabled(false);
//        etberat.setEnabled(false);
//        etKet.requestFocus();


    }

//    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
//        @Override
//        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//            switch (i){
//                case EditorInfo.IME_ACTION_SEND:
//                        Hitung();
//                        btKirim.setVisibility(View.VISIBLE);
//            }
//            return false;
//        }
//    };
//
//    private void Hitung(){
//        //convert string ke int
//        String Sharga = tvHarga.getText().toString();
//        int Iharga = Integer.parseInt(Sharga);
//
//        String Sjum = etKet.getText().toString();
//        int Ijum = Integer.parseInt(Sjum);
//
//        int x = Iharga * Ijum;
//        String total = String.valueOf(x);
//        etHarga.setText(total);
//    }
//
//    private void InsertData() {
//        final String txtInvoice = etId.getText().toString().trim();
//        final String txtkurir = txtIdKurir.getText().toString().trim();
//        final String txtNama = etTelp.getText().toString().trim();
//        final String txtTgl = etTanggal.getText().toString().trim();
//        final String txtStat = txtStatus.getText().toString().trim();
//
//        StringRequest request = new StringRequest(Request.Method.POST, InsertTugas,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.equalsIgnoreCase("success")) {
//                            Toast.makeText(KurirAntarDetail.this, "Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(KurirAntarDetail.this, UserDashboardActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(KurirAntarDetail.this, "Gagal", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(KurirAntarDetail.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id_transaksi", txtInvoice);
//                params.put("id_kurir", txtkurir);
//                params.put("nama", txtTgl);
//                params.put("tanggal", txtNama);
//                params.put("status", txtStat);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(KurirAntarDetail.this);
//        requestQueue.add(request);
//    }
//
//    private void SaveEditDetail() {
//
////        final String merk = this.etMerk.getText().toString().trim();
////        final String nama = this.etNama.getText().toString().trim();
////        final String warna = this.etWarna.getText().toString().trim();
////        final String plat = this.etPlat.getText().toString().trim();
////        final String tahun = this.etTahun.getText().toString().trim();
//        final String status = this.txtStatus.getText().toString().trim();
//        final String harga = this.etHarga.getText().toString().trim();
//        final String invoice = this.etId.getText().toString().trim();
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Saving...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, EditStatus,
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
//                                Toast.makeText(KurirAntarDetail.this, "Success!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(KurirAntarDetail.this, KurirDashboardActivity.class);
//                                startActivity(intent);
////                                sessionManager.createSession(email, name, id);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                            Toast.makeText(KurirAntarDetail.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(KurirAntarDetail.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("status", status);
//                params.put("harga", harga);
//                params.put("invoice", invoice);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }
}