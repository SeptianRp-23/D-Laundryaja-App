package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.Controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.dlaundryaja.R;

import java.util.List;

public class TrackingAdapter extends ArrayAdapter<ItemData> {

    Context ctx;
    List<ItemData> arrayItemData;

    public TrackingAdapter(@NonNull Context ctx, List<ItemData> arrayItemData) {
        super(ctx, R.layout.custom_tracking_detail, arrayItemData);

        this.ctx = ctx;
        this.arrayItemData = arrayItemData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tracking_detail, null, true);

        TextView tvTanggal = view.findViewById(R.id.ctd_tanggal);
        TextView tvID = view.findViewById(R.id.ctd_id);
        TextView tvNama = view.findViewById(R.id.ctd_nama);
        TextView tvLevel = view.findViewById(R.id.ctd_level);
        TextView tvStatus = view.findViewById(R.id.ctd_status);

        tvTanggal.setText(arrayItemData.get(position).getTrTanggal());
        tvID.setText(arrayItemData.get(position).getTrId_user());
        tvNama.setText(arrayItemData.get(position).getTrNama_user());
        tvLevel.setText(arrayItemData.get(position).getTrLevel());
        tvStatus.setText(arrayItemData.get(position).getTrStatus());
        return view;
    }
}
