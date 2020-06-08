package com.android.dlaundryaja.Test.BottomSheetDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.dlaundryaja.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetRegist extends BottomSheetDialogFragment {

    public BottomSheetRegist(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.layout_bottom_register, container, false);

        Button btnLogin = view1.findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Email = view1.findViewById(R.id.txtEmail);
                EditText Pass = view1.findViewById(R.id.txtPass);

                if (!TextUtils.isEmpty(Email.getText().toString()) || !TextUtils.isEmpty(Pass.getText().toString())) {
                    String itemEmail = Email.getText().toString();
                    String itemPass = Pass.getText().toString();
                    startActivity(new Intent(getContext(), HomePage.class).putExtra("data", itemEmail));
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Field Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view1;
    }
}
