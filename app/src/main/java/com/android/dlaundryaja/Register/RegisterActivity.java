package com.android.dlaundryaja.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserDashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.UserPemesananActivity;
import com.android.dlaundryaja.Login.LoginActivity;
import com.android.dlaundryaja.R;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText mtNama, mtEmail, mtTgl, mtTelp, mtAlamat, mtPass, mtCPass;
    Button btRegist;
    ImageView imgBack, imgTgl;
    TextView tvLevel;
    String myFormat = "EEEE, dd MMMM yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    private String InsertData = Api.URL_API + "register.php";
//    Calendar myCalendar = Calendar.getInstance();
//    TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mtNama      = findViewById(R.id.reg_nama);
        mtEmail     = findViewById(R.id.reg_email);
        mtTgl       = findViewById(R.id.reg_tanggal);
        mtTelp      = findViewById(R.id.reg_telp);
        mtAlamat    = findViewById(R.id.reg_alamat);
        mtPass      = findViewById(R.id.reg_pass);
        mtCPass     = findViewById(R.id.reg_c_pass);
        tvLevel     = findViewById(R.id.reg_level);
        btRegist    = findViewById(R.id.reg_btn_regist);
        imgBack     = findViewById(R.id.back);
        imgTgl      = findViewById(R.id.reg_img_tgl);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        imgTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(RegisterActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        mtTgl.setText(sdf.format(c1.getTime()));
        //end

        btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = mtNama.getText().toString();
                final String email = mtEmail.getText().toString();
                final String tgl = mtTgl.getText().toString();
                final String telp = mtTelp.getText().toString();
                final String alamat = mtAlamat.getText().toString();
                final String pass = mtPass.getText().toString();
                final String cpass = mtCPass.getText().toString();

                if (nama.isEmpty() || email.isEmpty() || tgl.isEmpty() || telp.isEmpty() || alamat.isEmpty() || pass.isEmpty() || cpass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(cpass)){
                    Toast.makeText(RegisterActivity.this, "Password Beda", Toast.LENGTH_SHORT).show();
                }
                else{
                    InsertData();
                }
            }
        });

    }

//    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            // TODO Auto-generated method stub
//            myCalendar.set(Calendar.YEAR, year);
//            myCalendar.set(Calendar.MONTH, monthOfYear);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            myCalendar.setTimeZone(tz);
//            setBirth();
//        }
//
//    };

//    private void setBirth() {
//        String myFormat = "EEEE, dd MMMM yyyy"; //In which you need put here
////        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
////        sdf.setTimeZone(tz);
//        mtTgl.setText(sdf.format(myCalendar.getTime()));
//    }

    private void InsertData() {
        final String etNama = mtNama.getText().toString().trim();
        final String etEmail = mtEmail.getText().toString().trim();
        final String etTgl = mtTgl.getText().toString().trim();
        final String etTelp = mtTelp.getText().toString().trim();
        final String etAlamat = mtAlamat.getText().toString().trim();
        final String EtPass = mtPass.getText().toString().trim();
        final String etLevel = tvLevel.getText().toString().trim();
        final Handler handler = new Handler();

        final SweetAlertDialog berhasil = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Menyimpan...");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                berhasil.setTitleText("Berhasil..")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }

        }, 3000);

        final SweetAlertDialog gagal = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
//                .setTitleText("Ooopss...")
//                .setContentText("Internet Tidak Stabil..");

        StringRequest request = new StringRequest(Request.Method.POST, InsertData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("sucess")) {
//                        Toast.makeText(UserPemesananActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            berhasil.show();
                        } else if (response.equalsIgnoreCase("failed")) {
                        Toast.makeText(RegisterActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Koneksi Internet Bermasalah!");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Format Email Salah")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Email Salah", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Format Email Tidak Di Kenali");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Email Sudah Terdaftar")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                            gagal.setTitleText("Ooops...");
                            gagal.setContentText("Email Sudah Terdaftar");
                            gagal.show();
                        } else if (response.equalsIgnoreCase("Kata Sandi Minimal 8 Karakter")) {
//                        Toast.makeText(UserPemesananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Kata Sandi", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisterActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(request);
    }
}