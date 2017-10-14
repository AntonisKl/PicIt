package com.example.android.picit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by heliix on 10/14/2017.
 */

public class HistoryAdapter extends ArrayAdapter<HistoryEntry> {
    private final Context context;
    private final ArrayList<HistoryEntry> entries;

    public HistoryAdapter(Context context, ArrayList<HistoryEntry> entries){
        super(context,-1,entries);
        this.context = context;
        this.entries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.history_item, parent, false);

        TextView nameView = (TextView) itemView.findViewById(R.id.product_name);
        TextView dateView = (TextView) itemView.findViewById(R.id.date);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.product_image);

        nameView.setText(entries.get(position).getpName());
        dateView.setText(entries.get(position).getDate());
        imageView.setImageResource(entries.get(position).getImgId());

        return itemView;
    }
}
