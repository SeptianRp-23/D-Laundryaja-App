package com.android.dlaundryaja.Activity.PageUser.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserPemesananActivity extends AppCompatActivity {

    private static final String TAG = UserPemesananActivity.class.getSimpleName() ;
    private String[] jenis = {"Select", "Rumah", "Kontrakan", "Kost", "Kantor", "Sekolah", "Lainnya"};
    MaterialEditText etId, etJenis, etTanggal, etNama, etTelp, etAlamat, etDetail, ethargapesanan, etSatuan;
    String myFormat = "dd-MM-yyy hh:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    ImageView btEdit;
    Spinner etLokasi;
    SessionManager sessionManager;
    String getId, getNama, getTelp, getAlamat;
    TextView tvKet, tvStatus;
    private String InsertData = Api.URL_API + "insertData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pemesanan);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        getNama = user.get(SessionManager.NAME);
        getTelp = user.get(SessionManager.TELP);
        getAlamat = user.get(SessionManager.ALAMAT);

        btEdit = findViewById(R.id.edit);
        etNama = findViewById(R.id.pem_nama);
        etTelp = findViewById(R.id.pem_telp);
        etAlamat = findViewById(R.id.pem_alamat);
        etDetail = findViewById(R.id.detail);
        etTanggal = findViewById(R.id.tanggal);
        etJenis = findViewById(R.id.jenis);
        etId = findViewById(R.id.id_pelanggan);
        etLokasi = findViewById(R.id.jenis_lokasi);
        tvKet = findViewById(R.id.keterangan);
        tvStatus = findViewById(R.id.stat);
        ethargapesanan = findViewById(R.id.hargapesanan);
        etSatuan = findViewById(R.id.txt_satuan);

        etId.setText(getId);
        etNama.setText(getNama);
        etTelp.setText(getTelp);
        etAlamat.setText(getAlamat);

        //Disable Editext
        etJenis.setEnabled(false);
        etTanggal.setEnabled(false);
        etId.setEnabled(false);
        etNama.setEnabled(false);
        etTelp.setEnabled(false);
        etAlamat.setEnabled(false);
        ethargapesanan.setEnabled(false);
        etSatuan.setEnabled(false);

        //Set Jenis
        etJenis.setText(getIntent().getStringExtra("jenis"));
        final String txtJenis = this.etJenis.getText().toString();
        if (txtJenis.equals("Laundry Prioritas")){
            ethargapesanan.setText("15000");
            etSatuan.setText("/Kilo");
        }
        else if (txtJenis.equals("Cuci Lengkap")){
            ethargapesanan.setText("10000");
            etSatuan.setText("/Kilo");
        }
        else if (txtJenis.equals("Setrika")){
            ethargapesanan.setText("5000");
            etSatuan.setText("/15 Pcs");
        }
        else if (txtJenis.equals("Cuci Sepatu")){
            ethargapesanan.setText("30000");
            etSatuan.setText("/Sepatu");
        }

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        etTanggal.setText(str1);
        //end

        final Button btSubmit = findViewById(R.id.submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mNama = etNama.getText().toString().trim();
                final String mTelp = etTelp.getText().toString().trim();
                final String mAlamat = etAlamat.getText().toString().trim();
                final String mDetail = etDetail.getText().toString().trim();

                if (mNama.isEmpty() || mTelp.isEmpty() || mAlamat.isEmpty() || mDetail.isEmpty()){
                Toast.makeText(UserPemesananActivity.this, "Field Belum Terpenuhi", Toast.LENGTH_SHORT).show();
            }else{
                    InsertData();
                }
            }
        });

        //Spinner
        final Spinner list = findViewById(R.id.jenis_lokasi);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, jenis);
        list.setAdapter(adapter);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = list.getSelectedItem().toString();
                if (value.equals("Select")){

                }else {
                    Toast.makeText(UserPemesananActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //end Spinner

        //Show Hide Item
        final ImageView atas = findViewById(R.id.row_atas);
        final ImageView bawah = findViewById(R.id.row_bawah);
        final LinearLayout layoutData = findViewById(R.id.data_pelanggan);
        layoutData.setVisibility(View.GONE);

        atas.setVisibility(View.GONE);
        bawah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bawah.setVisibility(View.GONE);
                atas.setVisibility(View.VISIBLE);
                btEdit.setVisibility(View.VISIBLE);
                layoutData.setVisibility(View.VISIBLE);
            }
        });
        atas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bawah.setVisibility(View.VISIBLE);
                atas.setVisibility(View.GONE);
                btEdit.setVisibility(View.INVISIBLE);
                layoutData.setVisibility(View.GONE);
            }
        });
        //End Show Hide

        //back function
        final ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPemesananActivity.this, UserDashboardActivity.class));
            }
        });
        //end

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNama.setEnabled(true);
                etTelp.setEnabled(true);
                etAlamat.setEnabled(true);
            }
        });
    }

        private void InsertData() {
            final String txtId = etId.getText().toString().trim();
            final String txtJenis = etJenis.getText().toString().trim();
            final String txtTgl = etTanggal.getText().toString().trim();
            final String txtNama = etNama.getText().toString().trim();
            final String txtTelp = etTelp.getText().toString().trim();
            final String txtAlamat = etAlamat.getText().toString().trim();
            final String txtLokasi = etLokasi.getSelectedItem().toString().trim();
            final String txtDetail = etDetail.getText().toString().trim();
            final String txtHarga = ethargapesanan.getText().toString().trim();
            final String txtKet = tvKet.getText().toString().trim();
            final String txtStatus = tvStatus.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, InsertData,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("success")) {
                        Toast.makeText(UserPemesananActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserPemesananActivity.this, UserDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                },
                new Response.ErrorListener() {
                @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserPemesananActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_user", txtId);
                        params.put("jenis", txtJenis);
                        params.put("tanggal", txtTgl);
                        params.put("nama", txtNama);
                        params.put("telp", txtTelp);
                        params.put("alamat", txtAlamat);
                        params.put("lokasi", txtLokasi);
                        params.put("detail", txtDetail);
                        params.put("harga", txtHarga);
                        params.put("keterangan", txtKet);
                        params.put("status", txtStatus);
                        return params;
                    }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(UserPemesananActivity.this);
            requestQueue.add(request);
        }
}