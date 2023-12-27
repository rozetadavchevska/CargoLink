package com.ap.cargolink.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ap.cargolink.R;
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
    public OffersAdapter(List<String> offersList, String senderOrder){
        this.offersList = offersList;
        this.senderOrder = senderOrder;
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
    }

    private void getOfferDetails(String offerId, ViewHolder holder) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("offers").child(offerId);
        offersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String driverId = snapshot.child("driverId").getValue(String.class);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            driverNameText = itemView.findViewById(R.id.driverNameText);
            deliveryDateText = itemView.findViewById(R.id.deliveryDateText);
            deliveryPriceText = itemView.findViewById(R.id.deliveryPriceText);
        }
    }
}
