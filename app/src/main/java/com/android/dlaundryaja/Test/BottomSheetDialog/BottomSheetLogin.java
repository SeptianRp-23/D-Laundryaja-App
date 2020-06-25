package com.android.dlaundryaja.Test.BottomSheetDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Server.Rest.Api;
import com.android.dlaundryaja.Utils.Controller.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

public class BottomSheetLogin extends BottomSheetDialogFragment {

//    public BottomSheetLogin(){
//
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final View view1 = inflater.inflate(R.layout.layout_bottom_login, container, false);
//
//        Button btnLogin = view1.findViewById(R.id.btn_Login);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MaterialEditText email = view1.findViewById(R.id.txtEmail);
//                MaterialEditText password = view1.findViewById(R.id.txtPass);
//
//                if (!TextUtils.isEmpty(email.getText().toString()) || !TextUtils.isEmpty(password.getText().toString())){
//                    String itemEmail = email.getText().toString();
//                    String itemPass = password.getText().toString();
//                    startActivity(new Intent(getContext(), HomePage.class).putExtra("data", itemEmail));
//                    dismiss();
//                }
//                else {
//                    Toast.makeText(getContext(), "Field Required", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        return view1;
//    }

}
