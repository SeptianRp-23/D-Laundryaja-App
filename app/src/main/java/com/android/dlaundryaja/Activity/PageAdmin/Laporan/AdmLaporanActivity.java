package com.android.dlaundryaja.Activity.PageAdmin.Laporan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.dlaundryaja.Activity.PageAdmin.Akun.AdmAkunActivity;
import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmDashboardActivity;
import com.android.dlaundryaja.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdmLaporanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_laporan);


        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.report);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.adm_dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                AdmDashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.report:
//                        startActivity(new Intent(getApplicationContext(),
//                                AdmLaporanActivity.class));
//                        overridePendingTransition(0,0);
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
}