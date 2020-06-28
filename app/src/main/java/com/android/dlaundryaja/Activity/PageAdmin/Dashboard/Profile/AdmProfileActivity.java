package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.dlaundryaja.Activity.PageAdmin.Dashboard.AdmMenuDashboard;
import com.android.dlaundryaja.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AdmProfileActivity extends AppCompatActivity {

    ImageView btBack, imgTgl;
    CardView cdAdd, cdEdit, formAdd, formEdit;
    Button btSimpanEdit, btSimpanAdd;
    LinearLayout menu;
    MaterialEditText etEdtNama, etEdtEmail, etEdtTgl, etEdtTelp, etEdtAlamat, etAddNama, etAddEmail, etAddLevel, etAddTgl, etAddTelp, etAddAlamat;
    Spinner spnLevel;

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
                btSimpanAdd.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
                formEdit.setVisibility(View.VISIBLE);
                formAdd.setVisibility(View.GONE);
            }
        });
    }
}