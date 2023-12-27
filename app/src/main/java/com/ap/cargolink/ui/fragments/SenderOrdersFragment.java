package com.ap.cargolink.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ap.cargolink.R;
import com.ap.cargolink.data.adapters.SenderOrdersAdapter;
import com.ap.cargolink.data.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SenderOrdersFragment extends Fragment {
    private List<Order> senderOrdersList;
    private SenderOrdersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sender_orders, container, false);

        RecyclerView ordersSender = view.findViewById(R.id.ordersList);
        FragmentManager fragmentManager = getParentFragmentManager();
        senderOrdersList = new ArrayList<>();

        getSenderOrders();
        adapter = new SenderOrdersAdapter(senderOrdersList, fragmentManager);
        ordersSender.setLayoutManager(new LinearLayoutManager(requireContext()));
        ordersSender.setAdapter(adapter);

        return view;
    }

    private void getSenderOrders() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Order senderOrder = dataSnapshot.getValue(Order.class);
                    String senderId = dataSnapshot.child("senderId").getValue(String.class);
                    if(currentUserId.equals(senderId)){
                        senderOrdersList.add(senderOrder);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}