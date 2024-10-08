package com.example.addressbooksystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.addressbooksystem.R;
import com.example.addressbooksystem.activity.DetailsActivity;
import com.example.addressbooksystem.bean.PeoBean;

import java.util.List;

public class PeoAdapter extends ArrayAdapter<PeoBean> {
    List<PeoBean> items;

    public PeoAdapter(Context context, List<PeoBean> items2) {
        super(context, R.layout.book_view, items2);
        this.items = items2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_view, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.tp);
        TextView zm = (TextView) convertView.findViewById(R.id.zm);
        final PeoBean peo = this.items.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(peo.getName());
        if (peo.getSex().equals("男")) {
            imageView.setImageResource(R.drawable.man);
        } else if (peo.getSex().equals("女")) {
            imageView.setImageResource(R.drawable.wuman);
        }
        if (position == 0) {
            zm.setText(peo.getBeginZ());
        } else if (!this.items.get(position - 1).getBeginZ().equals(peo.getBeginZ())) {
            zm.setText(peo.getBeginZ());
        } else {
            zm.setHeight(1);
        }
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(PeoAdapter.this.getContext(), DetailsActivity.class);
            intent.putExtra("id", peo.getId());
            PeoAdapter.this.getContext().startActivity(intent);
        });
        return convertView;
    }
}