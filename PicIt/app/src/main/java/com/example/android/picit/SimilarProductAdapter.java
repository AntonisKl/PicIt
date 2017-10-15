package com.example.android.picit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.picit.SchemaClasses.Product;

import java.util.ArrayList;

/**
 * Created by antonis on 15-Oct-17.
 */

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private Context context;
    private int productId;

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
    public SimilarProductAdapter(Context context, ArrayList<Product> products, int productId) {
        this.products = products;
        this.context = context;
        this.productId = productId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SimilarProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.similar_product_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ResultsFragment resultsFragment = ResultsFragment.newInstance(productId);
                fragmentTransaction.replace(R.id.content, resultsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

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
        holder.textView.setText(products.get(position).getProductName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}
