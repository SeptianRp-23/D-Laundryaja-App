package com.android.dlaundryaja.Activity.PageUser.Status.History;

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

public class AdapterHistory extends ArrayAdapter<ItemHistory> {
    Context context;
    List<ItemHistory> itemHistoryList;

    public AdapterHistory(@NonNull Context context, List<ItemHistory> itemHistoryList) {
        super(context, R.layout.custom_lilst_history, itemHistoryList);

        this.context = context;
        this.itemHistoryList = itemHistoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_lilst_history, null, true);

        TextView tvInv = view.findViewById(R.id.txt_invoice_history);
        TextView tvJenis = view.findViewById(R.id.txt_jenis_history);
        TextView tvTgl = view.findViewById(R.id.txt_tgl_history);
        TextView tvStatus = view.findViewById(R.id.txt_status_history);

        tvInv.setText(itemHistoryList.get(position).getInvoice());
        tvStatus.setText(itemHistoryList.get(position).getStatus());
        tvJenis.setText(itemHistoryList.get(position).getJenis());
        tvTgl.setText(itemHistoryList.get(position).getTanggal());

        return view;
    }
}
