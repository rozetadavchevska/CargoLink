package com.ap.cargolink.ui.fragments;

import static com.ap.cargolink.utils.NotificationReceiver.fetchUserTypeFromFirebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Offer;
import com.ap.cargolink.ui.activities.DriverActivity;
import com.ap.cargolink.ui.activities.SenderActivity;
import com.ap.cargolink.utils.NotificationHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeAnOfferFragment extends Fragment {
    private String orderId;
    private String orderSender;
    private String senderFullName;
    private String orderReceiver;
    private String deliveryDate;
    private String deliveryPrice;
    EditText offerDate;
    EditText offerPrice;
    TextView senderName;
    TextView receiverName;
    Button makeAnOffer;

    public static MakeAnOfferFragment newInstance(String orderItemId, String orderItemSender,String orderItemReceiver) {
        MakeAnOfferFragment fragment = new MakeAnOfferFragment();
        Bundle args = new Bundle();
        args.putString("orderId", orderItemId);
        args.putString("orderSender", orderItemSender);
        args.putString("orderReceiver", orderItemReceiver);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_an_offer, container, false);

        ImageButton back = view.findViewById(R.id.makeOfferBack);
        back.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        Bundle args = getArguments();
        if (args != null) {
            orderId = args.getString("orderId");
            orderSender = args.getString("orderSender");
            orderReceiver = args.getString("orderReceiver");
        }

        offerDate = view.findViewById(R.id.deliveryDateInput);
        offerPrice = view.findViewById(R.id.deliveryPriceInput);
        makeAnOffer = view.findViewById(R.id.makeAnOfferBtn);

        getSenderName();
        senderName = view.findViewById(R.id.senderName);
        receiverName = view.findViewById(R.id.receiverName);
        receiverName.setText(orderReceiver);

        makeAnOffer.setOnClickListener(v -> addOffer());

        return view;
    }

    private void addOffer(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        deliveryDate = offerDate.getText().toString();
        deliveryPrice = offerPrice.getText().toString();

        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("offers");
        String offerId = offersRef.push().getKey();
        Offer newOffer = new Offer();
        newOffer.setOfferId(offerId);
        newOffer.setOrderId(orderId);
        newOffer.setSenderId(orderSender);
        newOffer.setReceiver(orderReceiver);
        newOffer.setDriverId(currentUserId);
        newOffer.setDeliveryDate(deliveryDate);
        newOffer.setDeliveryPrice(deliveryPrice);

        offersRef.child(offerId).setValue(newOffer)
                .addOnSuccessListener(v -> {
                    Toast.makeText(getContext(),"Successfully added offer", Toast.LENGTH_SHORT).show();
                    updateOrderWithOffer(offerId, orderId);
                    makeAnOffer.setText("Offer made");
                    makeAnOffer.setEnabled(false);

                })
                .addOnFailureListener(v -> {
                    Toast.makeText(getContext(),"Problem adding order", Toast.LENGTH_SHORT).show();
                });

        scheduleNotificationForNewOffer();
    }

    private void updateOrderWithOffer(String newOfferId, String newOrderId) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(newOrderId);
        ordersRef.child("offersIds").child(newOfferId).setValue(true);
    }

    private void getSenderName(){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(orderSender);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);
                    senderFullName = firstName + " " + lastName;
                    senderName.setText(senderFullName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void scheduleNotificationForNewOffer() {
//        fetchUserTypeFromFirebase(getContext(), userType -> {
//            Class<?> targetActivity = userType.equals("Driver") ? SenderActivity.class : DriverActivity.class;
            NotificationHelper.showNotification(getContext(),  "New Offer Added", "A new offer has been added to your order!", SenderActivity.class);
//        });
    }
}