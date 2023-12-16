package com.ap.cargolink.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Order;

import java.util.List;

public class DriverHomeAdapter extends RecyclerView.Adapter<DriverHomeAdapter.ViewHolder> {
    private List<Order> recentOrdersList;
    public DriverHomeAdapter(List<Order> recentOrdersList){
        this.recentOrdersList = recentOrdersList;
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
        String orderItemName = orderItem.getOrderName();
        String orderItemDescription = orderItem.getOrderDescription();
        String orderItemFrom = orderItem.getAddressFrom();
        String orderItemTo = orderItem.getAddressTo();
        double orderItemWeight = orderItem.getOrderWeight();

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.orderName);
            orderDescription = itemView.findViewById(R.id.orderDescriptionText);
            orderAddressFrom = itemView.findViewById(R.id.orderFromAddressText);
            orderAddressTo = itemView.findViewById(R.id.orderToAddressText);
            orderWeight = itemView.findViewById(R.id.orderWeightText);
        }
    }
}
