package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.Login.LoginActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Register.RegisterActivity;
import com.android.dlaundryaja.Server.Local.Api;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdmProfileActivity extends AppCompatActivity {

    ImageView btBack, imgTgl;
    CardView cdAdd, cdEdit, formAdd, formEdit;
    Button btSimpanEdit, btSimpanAdd;
    LinearLayout menu;
    MaterialEditText etEdtNama, etEdtEmail, etEdtTgl, etEdtTelp, etEdtAlamat, etAddNama, etAddEmail, etAddLevel, etAddTgl, etAddTelp, etAddAlamat, etAddPass, etAddCPass;
    Spinner spnLevel;
    String myFormat = "EEEE, dd MMMM yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    private String InsertData = Api.URL_API + "register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_profile);

        cdAdd = findViewById(R.id.cardAdd);
        cdEdit = findViewById(R.id.cardEdit);
        btBack = findViewById(R.id.back);
        btSimpanEdit = findViewById(R.id.update);
        btSimpanAdd = findViewById(R.id.simpan);
        menu = findViewById(R.id.linearMenu);
        spnLevel = findViewById(R.id.add_spinner);
        imgTgl = findViewById(R.id.add_img_tgl);
        formAdd = findViewById(R.id.card1);
        formEdit = findViewById(R.id.card);
        etAddNama = findViewById(R.id.add_txt_nama);
        etAddEmail = findViewById(R.id.add_txt_email);
        etAddTelp = findViewById(R.id.add_txt_telp);
        etAddAlamat = findViewById(R.id.add_txt_alamat);
        etAddLevel = findViewById(R.id.add_txt_level);
        etAddTgl = findViewById(R.id.add_txt_tgl);
        etAddPass = findViewById(R.id.add_txt_password);
        etAddCPass = findViewById(R.id.add_txt_cpass);

        spnLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                final String value = String.valueOf(item);
                etAddLevel.setText(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cdAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSimpanAdd.setVisibility(View.VISIBLE);
                menu.setVisibility(View.GONE);
                formAdd.setVisibility(View.VISIBLE);
                formEdit.setVisibility(View.GONE);
            }
        });

        cdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSimpanEdit.setVisibility(View.VISIBLE);
                menu.setVisibility(View.GONE);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdmProfileActivity.this, AdmMenuDashboard.class));
            }
        });

        btSimpanEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSimpanEdit.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });

        btSimpanAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = etAddNama.getText().toString();
                final String email = etAddEmail.getText().toString();
                final String tgl = etAddTgl.getText().toString();
                final String telp = etAddTelp.getText().toString();
                final String alamat = etAddAlamat.getText().toString();
                final String pass = etAddPass.getText().toString();
                final String cpass = etAddCPass.getText().toString();
                final String level = etAddLevel.getText().toString();

                if (nama.isEmpty() || email.isEmpty() || tgl.isEmpty() || telp.isEmpty() || alamat.isEmpty() || pass.isEmpty() || cpass.isEmpty() || level.isEmpty()){
                    Toast.makeText(AdmProfileActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(cpass)){
                    Toast.makeText(AdmProfileActivity.this, "Password Beda", Toast.LENGTH_SHORT).show();
                }
                else{
                    InsertData();
                }
            }
        });

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        etAddTgl.setText(sdf.format(c1.getTime()));
        //end
    }

    private void InsertData() {
        final String etNama = etAddNama.getText().toString().trim();
        final String etEmail = etAddEmail.getText().toString().trim();
        final String etTgl = etAddTgl.getText().toString().trim();
        final String etTelp = etAddTelp.getText().toString().trim();
        final String etAlamat = etAddAlamat.getText().toString().trim();
        final String EtPass = etAddPass.getText().toString().trim();
        final String etLevel = etAddLevel.getText().toString().trim();
        final Handler handler = new Handler();

        final SweetAlertDialog berhasil = new SweetAlertDialog(AdmProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Menyimpan...");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                berhasil.setTitleText("Berhasil..")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(AdmProfileActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }

        }, 3000);

        final SweetAlertDialog gagal = new SweetAlertDialog(AdmProfileActivity.this, SweetAlertDialog.ERROR_TYPE);
//                .setTitleText("Ooopss...")
//                .setContentText("Internet Tidak Stabil..");

        StringRequest request = new StringRequest(Request.Method.POST, InsertData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("sucess")) {
//                        Toast.makeText(UserPemesananActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            berhasil.show();
                            btSimpanAdd.setVisibility(View.GONE);
                            menu.setVisibility(View.VISIBLE);
                            formEdit.setVisibility(View.VISIBLE);
                            formAdd.setVisibility(View.GONE);
                        } else if (response.equalsIgnoreCase("failed")) {
                            Toast.makeText(AdmProfileActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Koneksi Internet Bermasalah!");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Format Email Salah")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdmProfileActivity.this, "Email Salah", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Format Email Tidak Di Kenali");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Email Sudah Terdaftar")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdmProfileActivity.this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Email Sudah Terdaftar");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Kata Sandi Minimal 8 Karakter")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdmProfileActivity.this, "Kata Sandi", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Kata Sandi Minimal 6 Karakter");
                            gagal.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UserPemesananActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(AdmProfileActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        gagal.show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", etNama);
                params.put("email", etEmail);
                params.put("tgl", etTgl);
                params.put("telp", etTelp);
                params.put("alamat", etAlamat);
                params.put("password", EtPass);
                params.put("level", etLevel);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AdmProfileActivity.this);
        requestQueue.add(request);
    }
}