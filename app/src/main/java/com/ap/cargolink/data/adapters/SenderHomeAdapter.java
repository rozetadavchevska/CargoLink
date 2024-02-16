package com.ap.cargolink.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Order;

import java.util.List;

public class SenderHomeAdapter extends RecyclerView.Adapter<SenderHomeAdapter.ViewHolder> {
    private List<Order> senderOrdersList;
    private FragmentManager fragmentManager;
    private String nameOrder;
    private String descriptionOrder;
    private String addressFromOrder;
    private String addressToOrder;
    private String orderStatus;

    public SenderHomeAdapter(List<Order> senderOrdersList, FragmentManager fragmentManager){
        this.senderOrdersList = senderOrdersList;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender_home_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order orderItem = senderOrdersList.get(position);
        nameOrder = orderItem.getOrderName();
        descriptionOrder = orderItem.getOrderDescription();
        addressFromOrder = orderItem.getAddressFrom();
        addressToOrder = orderItem.getAddressTo();
        orderStatus = orderItem.getOrderStatus();

        if (!"Delivered".equals(orderStatus)) {
            holder.orderName.setText(nameOrder);
            holder.orderDescriptionText.setText(descriptionOrder);
            holder.orderFromAddressText.setText(addressFromOrder);
            holder.orderToAddressText.setText(addressToOrder);
            holder.orderStatusText.setText(orderStatus);
        } else {
            holder.itemView.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return senderOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderName, orderDescriptionText, orderFromAddressText, orderToAddressText, orderStatusText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderName = itemView.findViewById(R.id.orderName);
            orderDescriptionText = itemView.findViewById(R.id.orderDescriptionText);
            orderFromAddressText = itemView.findViewById(R.id.orderFromAddressText);
            orderToAddressText = itemView.findViewById(R.id.orderToAddressText);
            orderStatusText = itemView.findViewById(R.id.orderStatusText);
        }
    }
}
