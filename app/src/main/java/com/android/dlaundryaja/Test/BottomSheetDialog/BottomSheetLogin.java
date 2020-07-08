package com.android.dlaundryaja.Test.BottomSheetDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.dlaundryaja.Activity.PageUser.Status.UserStatusActivity;
import com.android.dlaundryaja.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetLogin extends BottomSheetDialogFragment {

    public BottomSheetLogin(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.layout_bottom_login, container, false);



        return view1;
    }

}
