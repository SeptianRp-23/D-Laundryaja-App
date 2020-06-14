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

public class PesananAdapter extends ArrayAdapter<DataPesanan> {

    Context context;
    List<DataPesanan> arrayListDataPesanan;

    public PesananAdapter(@NonNull Context context, List<DataPesanan> arrayListDataPesanan) {
        super(context, R.layout.custom_list_pesanan, arrayListDataPesanan);

        this.context = context;
        this.arrayListDataPesanan = arrayListDataPesanan;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_pesanan, null, true);

        TextView tvInv = view.findViewById(R.id.txt_invoice);
        TextView tvJen = view.findViewById(R.id.txt_jenis);
        TextView tvName = view.findViewById(R.id.txt_nama);
        TextView tvTgl = view.findViewById(R.id.txt_tanggal);

        tvInv.setText(arrayListDataPesanan.get(position).getInvoice());
        tvJen.setText(arrayListDataPesanan.get(position).getJenis());
        tvName.setText(arrayListDataPesanan.get(position).getTanggal());
        tvTgl.setText(arrayListDataPesanan.get(position).getNama());

        return view;
    }
}
