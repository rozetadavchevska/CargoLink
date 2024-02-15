package com.ap.cargolink.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewOrderFragment extends Fragment {
    EditText orderReceiver;
    EditText orderName;
    EditText orderDescription;
    EditText addressFrom;
    EditText addressTo;
    EditText senderNumber;
    EditText receiverNumber;
    EditText orderWeight;
    EditText orderPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);

        ImageButton back = view.findViewById(R.id.addOrderBack);
        back.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        orderReceiver = view.findViewById(R.id.orderReceiver);
        orderName = view.findViewById(R.id.orderName);
        orderDescription = view.findViewById(R.id.orderDescription);
        addressFrom = view.findViewById(R.id.orderFromAddress);
        addressTo = view.findViewById(R.id.orderToAddress);
        senderNumber = view.findViewById(R.id.senderPhone);
        receiverNumber = view.findViewById(R.id.receiverPhone);
        orderWeight = view.findViewById(R.id.orderWeight);
        orderPrice = view.findViewById(R.id.orderPrice);

        Button addOrder = view.findViewById(R.id.addOrderBtn);
        addOrder.setOnClickListener(v -> addNewOrder());

        return view;
    }

    private void addNewOrder(){
        String orderReceiverText = orderReceiver.getText().toString();
        String orderNameText = orderName.getText().toString();
        String orderDescriptionText = orderDescription.getText().toString();
        String addressFromText = addressFrom.getText().toString();
        String addressToText = addressTo.getText().toString();
        String senderNumberText = senderNumber.getText().toString();
        String receiverNumberText = receiverNumber.getText().toString();
        String orderWeightText = orderWeight.getText().toString();
        double orderWeightInput = Double.parseDouble(orderWeightText);
        String orderPriceText = orderPrice.getText().toString();

        if(orderReceiverText.isEmpty()||orderNameText.isEmpty()||orderDescriptionText.isEmpty()||addressFromText.isEmpty()||
        addressToText.isEmpty()||senderNumberText.isEmpty()||receiverNumberText.isEmpty()||orderWeightText.isEmpty()){
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        String orderId = ordersRef.push().getKey();
        String orderStatus = "Awaiting driver";
        Order newOrder = new Order();
        newOrder.setOrderId(orderId);
        newOrder.setOrderReceiver(orderReceiverText);
        newOrder.setOrderName(orderNameText);
        newOrder.setOrderDescription(orderDescriptionText);
        newOrder.setAddressFrom(addressFromText);
        newOrder.setAddressTo(addressToText);
        newOrder.setSenderNumber(senderNumberText);
        newOrder.setReceiverNumber(receiverNumberText);
        newOrder.setOrderWeight(orderWeightInput);
        newOrder.setOrderPrice(orderPriceText);
        newOrder.setSenderId(currentUserId);
        newOrder.setOrderStatus(orderStatus);
        if(orderId != null){
            ordersRef.child(orderId).setValue(newOrder)
                    .addOnSuccessListener(v -> {
                        Toast.makeText(getContext(),"Successfully added order", Toast.LENGTH_SHORT).show();
                        updateSenderDatabase(currentUserId, orderId);
                    })
                    .addOnFailureListener(v -> {
                        Toast.makeText(getContext(),"Problem adding order", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateSenderDatabase(String userId, String orderId){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        usersRef.child("ordersId").child(orderId).setValue(true);
    }
}