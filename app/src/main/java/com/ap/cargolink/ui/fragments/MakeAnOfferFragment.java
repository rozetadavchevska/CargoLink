package com.ap.cargolink.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ap.cargolink.R;

public class MakeAnOfferFragment extends Fragment {
    private String orderId;
    private String orderName;
    private String orderDescription;
    private String orderFrom;
    private String orderTo;
    private String orderSender;
    private String orderStatus;
    private String orderWeight;

    public static MakeAnOfferFragment newInstance(String orderItemId, String orderItemName, String orderItemDescription, String orderItemFrom,String orderItemTo, double orderItemWeight, String orderItemSender,String orderItemStatus) {
        MakeAnOfferFragment fragment = new MakeAnOfferFragment();
        Bundle args = new Bundle();
        args.putString("orderId", orderItemId);
        args.putString("orderName", orderItemName);
        args.putString("orderDescription", orderItemDescription);
        args.putString("orderFrom", orderItemFrom);
        args.putString("orderTo", orderItemTo);
        args.putString("orderWeight", String.valueOf(orderItemWeight));
        args.putString("orderSender", orderItemSender);
        args.putString("orderStatus", orderItemStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_an_offer, container, false);

        Bundle args = getArguments();
        if (args != null) {
            orderId = args.getString("orderId");
            orderName = args.getString("orderName");
            orderDescription = args.getString("orderDescription");
            orderFrom = args.getString("orderFrom");
            orderTo = args.getString("orderTo");
            orderWeight = args.getString("orderWeight");
            orderSender = args.getString("orderSender");
            orderStatus = args.getString("orderStatus");
        }


        return view;
    }
}