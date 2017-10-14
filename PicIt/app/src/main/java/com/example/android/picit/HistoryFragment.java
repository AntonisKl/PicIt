package com.example.android.picit;

import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.picit.SchemaClasses.HistoryElement;
import com.example.android.picit.SchemaClasses.User;
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
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        ServerInterface serverService = ServerClient.getClient(getActivity().getApplicationContext()).create(ServerInterface.class);
        Call<List<HistoryElement>> historyCall = serverService.getHistory(User.getUserId(getActivity().getApplicationContext()));
        historyCall.enqueue(new Callback<List<HistoryElement>>() {
            @Override
            public void onResponse(Call<List<HistoryElement>> call, Response<List<HistoryElement>> response) {
                if (response.code()<300) {
                    List<HistoryElement> historyElements = response.body();
                    ArrayList<HistoryEntry> entries = new ArrayList<HistoryEntry>();
                    int i=0;
                    Log.d("ELEOS", ((Integer)historyElements.size()).toString());
                    for (;i<historyElements.size();i++) {
                        entries.add(new HistoryEntry(historyElements.get(i).getPictureId(), historyElements.get(i).getProductName(), historyElements.get(i).getDatetime(), historyElements.get(i).getProductId()));
                    }

                    HistoryAdapter adapter = new HistoryAdapter(getActivity(), entries);

                    ListView listView = (ListView) view.findViewById(R.id.history_list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<HistoryElement>> call, Throwable t) {

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
