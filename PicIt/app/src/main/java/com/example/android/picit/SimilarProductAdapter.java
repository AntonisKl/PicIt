package com.example.android.picit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by antonis on 15-Oct-17.
 */

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.ViewHolder> {

    private ArrayList<SimilarProduct> products;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.similar_product_image);
            textView = (TextView) v.findViewById(R.id.similar_product_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SimilarProductAdapter(ArrayList<SimilarProduct> products) {
        this.products = products;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SimilarProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.similar_product_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


//        ImageView imageView = (ImageView) view.findViewById(R.id.similar_product_image);
//        TextView textView = (TextView) view.findViewById(R.id.similar_product_name);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.imageView.setImageResource(products.get(position).getImgId());
        holder.textView.setText(products.get(position).getpName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}
