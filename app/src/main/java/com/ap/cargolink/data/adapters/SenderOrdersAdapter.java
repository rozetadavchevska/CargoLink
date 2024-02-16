package com.ap.cargolink.data.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Order;
import com.ap.cargolink.ui.fragments.ViewOffersFragment;

import java.util.List;

public class SenderOrdersAdapter extends RecyclerView.Adapter<SenderOrdersAdapter.ViewHolder> {
    private List<Order> senderOrdersList;
    private FragmentManager fragmentManager;
    private String nameOrder;
    private String descriptionOrder;
    private String addressFromOrder;
    private String addressToOrder;
    private String orderStatus;

    public SenderOrdersAdapter(List<Order> senderOrdersList, FragmentManager fragmentManager){
        this.senderOrdersList = senderOrdersList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender_order, parent, false);
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

        holder.orderName.setText(nameOrder);
        holder.orderDescriptionText.setText(descriptionOrder);
        holder.orderFromAddressText.setText(addressFromOrder);
        holder.orderToAddressText.setText(addressToOrder);
        holder.orderStatusText.setText(orderStatus);

        holder.viewOffersBtn.setOnClickListener(v -> {
            if (fragmentManager != null) {
                ViewOffersFragment viewOffersFragment = ViewOffersFragment.newInstance(orderItem.getOrderId(), orderItem.getOffersIds());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.senderFrameLayout, viewOffersFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }  else {
                Toast.makeText(v.getContext(), "No offers at the moment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return senderOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderName, orderDescriptionText, orderFromAddressText, orderToAddressText, orderStatusText;
        Button viewOffersBtn, reviewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderName = itemView.findViewById(R.id.orderName);
            orderDescriptionText = itemView.findViewById(R.id.orderDescriptionText);
            orderFromAddressText = itemView.findViewById(R.id.orderFromAddressText);
            orderToAddressText = itemView.findViewById(R.id.orderToAddressText);
            orderStatusText = itemView.findViewById(R.id.orderStatusText);
            viewOffersBtn = itemView.findViewById(R.id.viewOffersBtn);
            reviewBtn = itemView.findViewById(R.id.reviewBtn);
        }
    }
}
