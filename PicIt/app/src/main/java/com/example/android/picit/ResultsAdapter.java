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
 * Created by antonis on 14-Oct-17.
 */

public class ResultsAdapter extends ArrayAdapter<Result> {
    private final Context context;
    private final ArrayList<Result> results;

    public ResultsAdapter(Context context, ArrayList<Result> results){
        super(context,-1,results);
        this.context = context;
        this.results = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.results_item, parent, false);

        TextView nameView = (TextView) itemView.findViewById(R.id.product_name);
        TextView descriptionView = (TextView) itemView.findViewById(R.id.product_description);
        TextView priceView = (TextView) itemView.findViewById(R.id.price);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.shop_logo_results);

        nameView.setText(results.get(position).getpName());
        descriptionView.setText(results.get(position).getpDescription());
        priceView.setText("" + results.get(position).getPrice());
        imageView.setImageResource(results.get(position).getImgId());

        return itemView;
    }
}
