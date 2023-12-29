package com.ap.cargolink.data.adapters;

import static com.ap.cargolink.utils.NotificationReceiver.fetchUserTypeFromFirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
import com.ap.cargolink.ui.activities.DriverActivity;
import com.ap.cargolink.ui.activities.SenderActivity;
import com.ap.cargolink.utils.NotificationHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DriverOrdersAdapter extends RecyclerView.Adapter<DriverOrdersAdapter.ViewHolder> {
    private List<String> ordersList;
    private FragmentManager fragmentManager;
    private Context context;

    public DriverOrdersAdapter(List<String> ordersList, FragmentManager fragmentManager, Context context){
        this.ordersList = ordersList;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String orderId = ordersList.get(position);

        getOrderDetails(orderId, holder);

        checkOrderConfirmationStatus(orderId, holder);

        holder.confirmDeliveredBtn.setOnClickListener(v -> confirmDelivered(orderId, "Delivered"));
    }

    private void confirmDelivered(String orderId, String status) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
        ordersRef.child("orderStatus").setValue(status);

//        fetchUserTypeFromFirebase(context, userType -> {
//            Class<?> targetActivity = userType.equals("Driver") ? SenderActivity.class : DriverActivity.class;
            NotificationHelper.showNotification(context, "Order Confirmed", "Your order has been delivered!", SenderActivity.class);
//        });
    }

    private void checkOrderConfirmationStatus(String orderId, ViewHolder holder) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String orderStatus = snapshot.child("orderStatus").getValue(String.class);

                    if ("Delivered".equals(orderStatus)) {
                        holder.confirmDeliveredBtn.setText("Confirmed");
                        holder.confirmDeliveredBtn.setEnabled(false);
                    } else {
                        holder.confirmDeliveredBtn.setText("Confirm Delivered");
                        holder.confirmDeliveredBtn.setEnabled(true);
                        holder.confirmDeliveredBtn.setOnClickListener(v -> confirmDelivered(orderId, "Delivered"));
                    }
                }
                notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getOrderDetails(String orderId, ViewHolder holder) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("orderName").getValue(String.class);
                    String description = snapshot.child("orderDescription").getValue(String.class);
                    String addressFrom = snapshot.child("addressFrom").getValue(String.class);
                    String addressTo = snapshot.child("addressTo").getValue(String.class);
                    Long weight = snapshot.child("orderWeight").getValue(Long.class);
                    String receiver = snapshot.child("orderReceiver").getValue(String.class);
                    String receiverNumber = snapshot.child("receiverNumber").getValue(String.class);


                    holder.orderName.setText(name);
                    holder.orderDescriptionText.setText(description);
                    holder.orderFromAddressText.setText(addressFrom);
                    holder.orderToAddressText.setText(addressTo);
                    holder.orderWeightText.setText(String.valueOf(weight));
                    holder.orderReceiverText.setText(receiver);
                    holder.orderPhoneText.setText(receiverNumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderDescriptionText, orderFromAddressText, orderToAddressText, orderWeightText, orderReceiverText, orderPhoneText;
        Button confirmDeliveredBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderName = itemView.findViewById(R.id.orderName);
            orderDescriptionText = itemView.findViewById(R.id.orderDescriptionText);
            orderFromAddressText = itemView.findViewById(R.id.orderFromAddressText);
            orderToAddressText = itemView.findViewById(R.id.orderToAddressText);
            orderWeightText = itemView.findViewById(R.id.orderWeightText);
            orderReceiverText = itemView.findViewById(R.id.orderReceiverText);
            orderPhoneText = itemView.findViewById(R.id.orderPhoneText);
            confirmDeliveredBtn = itemView.findViewById(R.id.confirmDeliveredBtn);
        }
    }
}

