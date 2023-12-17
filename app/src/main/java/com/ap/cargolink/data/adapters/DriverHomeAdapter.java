package com.ap.cargolink.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Order;
import com.ap.cargolink.ui.fragments.MakeAnOfferFragment;

import java.util.List;

public class DriverHomeAdapter extends RecyclerView.Adapter<DriverHomeAdapter.ViewHolder> {
    private String orderItemId;
    private String orderItemName;
    private String orderItemDescription;
    private String orderItemFrom;
    private String orderItemTo;
    private String orderItemSender;
    private String orderItemStatus;
    private double orderItemWeight;
    private String orderReceiver;
    private List<Order> recentOrdersList;
    private FragmentManager fragmentManager;
    private boolean isOfferMade = false;
    public DriverHomeAdapter(List<Order> recentOrdersList, FragmentManager fragmentManager){
        this.recentOrdersList = recentOrdersList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order orderItem = recentOrdersList.get(position);
        orderItemId = orderItem.getOrderId();
        orderItemName = orderItem.getOrderName();
        orderItemDescription = orderItem.getOrderDescription();
        orderItemFrom = orderItem.getAddressFrom();
        orderItemTo = orderItem.getAddressTo();
        orderItemSender = orderItem.getSenderId();
        orderItemStatus = orderItem.getOrderStatus();
        orderItemWeight = orderItem.getOrderWeight();
        orderReceiver = orderItem.getOrderReceiver();

        if(orderItemName != null){
            holder.orderName.setText(orderItemName);
        }

        if(orderItemDescription != null){
            holder.orderDescription.setText(orderItemDescription);
        }

        if(orderItemFrom != null){
            holder.orderAddressFrom.setText(orderItemFrom);
        }

        if(orderItemTo != null){
            holder.orderAddressTo.setText(orderItemTo);
        }

        holder.orderWeight.setText(String.valueOf(orderItemWeight));

        holder.makeOffer.setOnClickListener(v -> makeAnOffer());
    }

    private void makeAnOffer(){
        if(fragmentManager != null){
            MakeAnOfferFragment makeAnOfferFragment = MakeAnOfferFragment.newInstance(orderItemId, orderItemSender,orderReceiver);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.driverFrameLayout, makeAnOfferFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public int getItemCount() {
        return recentOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderName;
        TextView orderDescription;
        TextView orderAddressFrom;
        TextView orderAddressTo;
        TextView orderWeight;
        Button makeOffer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.orderName);
            orderDescription = itemView.findViewById(R.id.orderDescriptionText);
            orderAddressFrom = itemView.findViewById(R.id.orderFromAddressText);
            orderAddressTo = itemView.findViewById(R.id.orderToAddressText);
            orderWeight = itemView.findViewById(R.id.orderWeightText);
            makeOffer = itemView.findViewById(R.id.makeAnOfferBtn);
        }
    }
}
