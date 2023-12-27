package com.ap.cargolink.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ap.cargolink.R;
import com.ap.cargolink.data.adapters.OffersAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewOffersFragment extends Fragment {
    private String orderId;
    private Map<String, Boolean> orderOffers;
    private List<String> offersList;
    private OffersAdapter adapter;
    public static ViewOffersFragment newInstance(String orderItemId, Map<String, Boolean> orderItemOffers) {
        ViewOffersFragment fragment = new ViewOffersFragment();
        Bundle args = new Bundle();
        args.putString("orderId", orderItemId);
        args.putSerializable("orderItemOffers", (Serializable) orderItemOffers);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_offers, container, false);

        ImageButton back = view.findViewById(R.id.viewOfferBack);
        back.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        Bundle args = getArguments();
        if (args != null) {
            orderId = args.getString("orderId");
            Serializable mapOffers = args.getSerializable("orderItemOffers");
            if (mapOffers instanceof Map) {
                orderOffers = (Map<String, Boolean>) mapOffers;
                offersList = new ArrayList<>();

                for (Map.Entry<String, Boolean> entry : orderOffers.entrySet()) {
                    offersList.add(entry.getKey());
                }
            }
        }

        RecyclerView studentsAttending = view.findViewById(R.id.viewOffers);
        adapter = new OffersAdapter(offersList, orderId);
        studentsAttending.setLayoutManager(new LinearLayoutManager(requireContext()));
        studentsAttending.setAdapter(adapter);

        return view;
    }
}