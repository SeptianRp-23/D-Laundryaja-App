package com.android.dlaundryaja.Utils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.dlaundryaja.R;
import com.android.dlaundryaja.Utils.Adapter.DataItem.DataPesanan;

import java.util.List;

public class TrackingAdapter extends ArrayAdapter<DataPesanan> {
    Context context;
    List<DataPesanan> arrayListDataPesanan;

    public TrackingAdapter(@NonNull Context context, List<DataPesanan> arrayListDataPesanan) {
        super(context, R.layout.custom_tracking, arrayListDataPesanan);

        this.context = context;
        this.arrayListDataPesanan = arrayListDataPesanan;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tracking, null, true);

        TextView tvInv = view.findViewById(R.id.txt_invoice);
        TextView tvName = view.findViewById(R.id.txt_nama);
        TextView tvTgl = view.findViewById(R.id.txt_tanggal);
        TextView tvStatus = view.findViewById(R.id.txt_status);

        tvInv.setText(arrayListDataPesanan.get(position).getInvoice());
        tvStatus.setText(arrayListDataPesanan.get(position).getStatus());
        tvName.setText(arrayListDataPesanan.get(position).getNama());
        tvTgl.setText(arrayListDataPesanan.get(position).getTanggal());

        return view;
    }
}
