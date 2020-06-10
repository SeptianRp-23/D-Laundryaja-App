package com.android.dlaundryaja.Activity.PageUser.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.dlaundryaja.Activity.PageUser.Account.AccountActivity;
import com.android.dlaundryaja.Activity.PageUser.Dashboard.DashboardActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.History.HistoryActivity;
import com.android.dlaundryaja.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //BottomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
//                        startActivity(new Intent(getApplicationContext(),
//                                StatusActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),
                                AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//        //end BottomNav
    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Intent intent = new Intent(StatusActivity.this, HistoryActivity.class);
//        startActivity(intent);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.adduser:
//                Intent intent = new Intent(StatusActivity.this, TambahUserActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.account:
//                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(i);
//                return true;
//            case R.id.logout:
//                Toast.makeText(this, "Terimakasih", Toast.LENGTH_SHORT).show();
//                logout();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}