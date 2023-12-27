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
import android.widget.Button;
import android.widget.TextView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.adapters.SenderHomeAdapter;
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
import java.util.Objects;

public class SenderHomeFragment extends Fragment {
    NewOrderFragment newOrderFragment = new NewOrderFragment();
    private List<Order> senderOrdersList;
    private SenderHomeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sender_home, container, false);

        TextView userName = view.findViewById(R.id.userName);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        getUserName(currentUserId, userName);

        Button newOrderBtn = view.findViewById(R.id.newOrderBtn);
        newOrderBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.senderFrameLayout, newOrderFragment).commit();
        });

        RecyclerView ordersSender = view.findViewById(R.id.ordersInProgress);
        FragmentManager fragmentManager = getParentFragmentManager();
        senderOrdersList = new ArrayList<>();

        getSenderOrders(currentUserId);
        adapter = new SenderHomeAdapter(senderOrdersList, fragmentManager);
        ordersSender.setLayoutManager(new LinearLayoutManager(requireContext()));
        ordersSender.setAdapter(adapter);

        return view;
    }

    private void getSenderOrders(String userId) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Order senderOrder = dataSnapshot.getValue(Order.class);
                    String senderId = dataSnapshot.child("senderId").getValue(String.class);
                    String orderStatus = dataSnapshot.child("orderStatus").getValue(String.class);
                    if(userId.equals(senderId) && !Objects.equals(orderStatus, "Complete")){
                        senderOrdersList.add(senderOrder);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getUserName(String currentUserId, TextView userName) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    userName.setText(firstName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}