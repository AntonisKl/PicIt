package com.example.android.picit;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.picit.SchemaClasses.Store;
import com.example.android.picit.ServerHandler.ServerClient;
import com.example.android.picit.ServerHandler.ServerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "productId";

    // TODO: Rename and change types of parameters
    private int productId;

    private OnFragmentInteractionListener mListener;



    public ResultsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ResultsFragment newInstance(int productId) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             this.productId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, container, false);
        

        ArrayList<SimilarProduct> similarProducts = new ArrayList<SimilarProduct>();
        similarProducts.add(new SimilarProduct(R.drawable.ic_home_black_24dp, "product name"));
        similarProducts.add(new SimilarProduct(R.drawable.ic_home_black_24dp, "product name"));
        similarProducts.add(new SimilarProduct(R.drawable.ic_home_black_24dp, "product name"));
        similarProducts.add(new SimilarProduct(R.drawable.ic_home_black_24dp, "product name"));

        SimilarProductAdapter similarProductAdapter = new SimilarProductAdapter(similarProducts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(similarProductAdapter);
        
        ServerInterface serverService = ServerClient.getClient(getActivity().getApplicationContext()).create(ServerInterface.class);
        Call<List<StoreResult>> storeCall = serverService.findStores(productId);
        storeCall.enqueue(new Callback<List<StoreResult>>() {
            @Override
            public void onResponse(Call<List<StoreResult>> call, Response<List<StoreResult>> response) {
                if (response.code()<300) {
                    List<StoreResult> stores = response.body();
                    final ResultsAdapter storesAdapter = new ResultsAdapter(getActivity(), (ArrayList)stores);
                    final ListView listView = (ListView) view.findViewById(R.id.results_list);
                    listView.setAdapter(storesAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ShopFragment fragment = ShopFragment.newInstance(((StoreResult) parent.getItemAtPosition(position)).getShopId(), productId);
                            fragmentTransaction.replace(R.id.content, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<StoreResult>> call, Throwable t) {

            }
        });

        // Inflate the layout for this fragment
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
