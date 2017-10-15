package com.example.android.picit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.picit.SchemaClasses.ShopProductElement;
import com.example.android.picit.ServerHandler.ServerClient;
import com.example.android.picit.ServerHandler.ServerInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int shopId;
    private int productId;

    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance(int shopId, int productId) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, shopId);
        args.putInt(ARG_PARAM2, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getInt(ARG_PARAM1);
            productId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ServerInterface serverService = ServerClient.getClient(getActivity().getApplicationContext()).create(ServerInterface.class);
        Call<ShopProductElement> detailsCall = serverService.getStoreProductDetails(shopId, productId);
        detailsCall.enqueue(new Callback<ShopProductElement>() {
            @Override
            public void onResponse(Call<ShopProductElement> call, Response<ShopProductElement> response) {
                if (response.code()<300) {
                    ShopProductElement spElem = response.body();
                    final ImageView logoImg = (ImageView)view.findViewById(R.id.shop_logo);
                    final TextView websiteText = (TextView)view.findViewById(R.id.shop_website);
                    final TextView productText = (TextView)view.findViewById(R.id.product_name);
                    final TextView availableText = (TextView)view.findViewById(R.id.availability);
                    final TextView priceText = (TextView)view.findViewById(R.id.price);
                    spElem.getsImage(logoImg, getActivity().getApplicationContext());
                    websiteText.setText(spElem.getUrl());
                    productText.setText(spElem.getpName());
                    availableText.setText("" + spElem.getQuantity());
                    priceText.setText("" + spElem.getPrice());
                }
            }

            @Override
            public void onFailure(Call<ShopProductElement> call, Throwable t) {
                Log.d("mytag", t.toString());
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
