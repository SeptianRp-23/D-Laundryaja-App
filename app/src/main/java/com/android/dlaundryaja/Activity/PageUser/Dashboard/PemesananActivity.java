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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PemesananActivity extends AppCompatActivity {

    private String[] jenis = {"Select", "Rumah", "Kontrakan", "Kost'an", "Kantor", "Sekolah", "Lainnya"};
    MaterialEditText etJenis, tanggal, nama, telp, alamat, detail;
    String myFormat = "dd-MM-yyy hh:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        nama = findViewById(R.id.nama_pelanggan);
        telp = findViewById(R.id.no_telp);
        alamat = findViewById(R.id.alamat);
        detail = findViewById(R.id.detail);
        tanggal = findViewById(R.id.tanggal);
        etJenis = findViewById(R.id.jenis);

        //Disable Editext
        etJenis.setEnabled(false);
        tanggal.setEnabled(false);

        //Set Jenis
        etJenis.setText(getIntent().getStringExtra("jenis"));

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdf.format(c1.getTime());
        tanggal.setText(str1);
        //end

        final Button btSubmit = findViewById(R.id.submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mNama = nama.getText().toString().trim();
                final String mTelp = telp.getText().toString().trim();
                final String mAlamat = alamat.getText().toString().trim();
                final String mDetail = detail.getText().toString().trim();

                if (mNama.isEmpty() || mTelp.isEmpty() || mAlamat.isEmpty() || mDetail.isEmpty()){
                Toast.makeText(PemesananActivity.this, "Field Belum Terpenuhi", Toast.LENGTH_SHORT).show();
            }else{
                    Toast.makeText(PemesananActivity.this, "Proses!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PemesananActivity.this, ""+value, Toast.LENGTH_SHORT).show();
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
                layoutData.setVisibility(View.VISIBLE);
            }
        });
        atas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bawah.setVisibility(View.VISIBLE);
                atas.setVisibility(View.GONE);
                layoutData.setVisibility(View.GONE);
            }
        });
        //End Show Hide

        //back function
        final ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PemesananActivity.this, DashboardActivity.class));
            }
        });
        //end
    }
}