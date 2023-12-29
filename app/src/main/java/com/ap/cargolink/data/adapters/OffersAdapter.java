package com.ap.cargolink.data.adapters;

import static com.ap.cargolink.utils.NotificationReceiver.fetchUserTypeFromFirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder>{
    private List<String> offersList;
    private String senderOrder;
    private String driverFullName;
    private String driverId;
    private Context context;
    public OffersAdapter(List<String> offersList, String senderOrder, Context context){
        this.offersList = offersList;
        this.senderOrder = senderOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String offer = offersList.get(position);

        getOfferDetails(offer, holder);

        DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("offers").child(offer);
        offerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String offerStatus = snapshot.child("offerStatus").getValue(String.class);

                    if ("Accepted".equals(offerStatus)) {
                        holder.acceptOfferBtn.setText("Accepted Offer");
                        holder.acceptOfferBtn.setClickable(false);
                    } else if ("Not Accepted".equals(offerStatus)) {
                        holder.acceptOfferBtn.setText("Not accepted");
                        holder.acceptOfferBtn.setClickable(false);
                    } else {
                        holder.acceptOfferBtn.setText("Accept Offer");
                        holder.acceptOfferBtn.setClickable(true);
                        holder.acceptOfferBtn.setOnClickListener(view -> acceptOffer(offer, holder.getAdapterPosition()));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void acceptOffer(String offerId, int position) {
        updateNotAcceptedOffers(position);
        updateOfferStatus(offerId, "Accepted");
        updateOrderStatus("Awaiting order");
        updateDriverOrders(driverId, senderOrder);

//        fetchUserTypeFromFirebase(context, userType -> {
//            Class<?> targetActivity = userType.equals("Driver") ? SenderActivity.class : DriverActivity.class;
            NotificationHelper.showNotification(context, "Offer Accepted", "Your offer has been accepted!", DriverActivity.class);
//        });
    }


    private void updateNotAcceptedOffers(int clickedPosition) {
        for (int i = 0; i < offersList.size(); i++) {
            if (i != clickedPosition) {
                String notAcceptedOfferId = offersList.get(i);
                updateOfferStatus(notAcceptedOfferId, "Not Accepted");
            }
        }
    }

    private void updateOfferStatus(String offerId, String status) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("offers").child(offerId);
        offersRef.child("offerStatus").setValue(status);
    }

    private void updateOrderStatus(String status) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders").child(senderOrder);
        orderRef.child("orderStatus").setValue(status);
    }

    private void updateDriverOrders(String driversId, String orderId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(driversId);
        usersRef.child("ordersId").child(orderId).setValue(true);
    }

    private void getOfferDetails(String offerId, ViewHolder holder) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("offers").child(offerId);
        offersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    driverId = snapshot.child("driverId").getValue(String.class);
                    String deliveryDate = snapshot.child("deliveryDate").getValue(String.class);
                    String deliveryPrice = snapshot.child("deliveryPrice").getValue(String.class);

                    getDriverName(driverId, holder);
                    holder.deliveryDateText.setText(deliveryDate);
                    holder.deliveryPriceText.setText(deliveryPrice);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getDriverName(String offerDriverId, ViewHolder holder) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(offerDriverId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);
                    if(firstName != null && lastName != null){
                        driverFullName = firstName + " " + lastName;
                        holder.driverNameText.setText(driverFullName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView driverNameText, deliveryDateText, deliveryPriceText;
        Button acceptOfferBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            driverNameText = itemView.findViewById(R.id.driverNameText);
            deliveryDateText = itemView.findViewById(R.id.deliveryDateText);
            deliveryPriceText = itemView.findViewById(R.id.deliveryPriceText);
            acceptOfferBtn = itemView.findViewById(R.id.acceptOfferBtn);
        }
    }
}
