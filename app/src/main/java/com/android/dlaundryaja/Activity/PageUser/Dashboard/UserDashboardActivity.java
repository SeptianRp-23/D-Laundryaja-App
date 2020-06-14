package com.android.dlaundryaja.Activity.PageUser.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.dlaundryaja.Activity.PageUser.Account.UserAccountActivity;
import com.android.dlaundryaja.Activity.PageUser.Status.UserStatusActivity;
import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String getID;
    SharedPreferences sharedPreferences;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);

        //Slider
        viewPager2 = findViewById(R.id.viewPagerImagerSlider);

        final List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.laundryaja));
        sliderItems.add(new SliderItem(R.drawable.phone));
        sliderItems.add(new SliderItem(R.drawable.kurir));
        sliderItems.add(new SliderItem(R.drawable.laundry));
        sliderItems.add(new SliderItem(R.drawable.shoes));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.30f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 5000);
            }
        });
        //End Slider


        //BottomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),
//                                MainActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),
                                UserStatusActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),
                                UserAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//        //end BottomNav

        final CardView card1 = findViewById(R.id.c_prioritas);
        final TextView tv1 = findViewById(R.id.c_tv_1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, UserPemesananActivity.class);
                intent.putExtra("jenis",tv1.getText().toString());
                startActivity(intent);
            }
        });
        final CardView card2 = findViewById(R.id.c_lengkap);
        final TextView tv2 = findViewById(R.id.c_tv_2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, UserPemesananActivity.class);
                intent.putExtra("jenis",tv2.getText().toString());
                startActivity(intent);
            }
        });
        final CardView card3 = findViewById(R.id.c_setrika);
        final TextView tv3 = findViewById(R.id.c_tv_3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, UserPemesananActivity.class);
                intent.putExtra("jenis",tv3.getText().toString());
                startActivity(intent);
            }
        });
        final CardView card4 = findViewById(R.id.c_sepatu);
        final TextView tv4 = findViewById(R.id.c_tv_4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, UserPemesananActivity.class);
                intent.putExtra("jenis",tv4.getText().toString());
                startActivity(intent);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 3000);
    }
}