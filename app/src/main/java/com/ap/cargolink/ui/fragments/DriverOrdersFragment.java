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
import com.ap.cargolink.data.adapters.DriverOrdersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverOrdersFragment extends Fragment {
    private List<String> ordersList;
    private DriverOrdersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_orders, container, false);

        RecyclerView orders = view.findViewById(R.id.ordersList);
        FragmentManager fragmentManager = getParentFragmentManager();
        ordersList = new ArrayList<>();
        getOrders();

        adapter = new DriverOrdersAdapter(ordersList, fragmentManager, requireContext());
        orders.setLayoutManager(new LinearLayoutManager(getContext()));
        orders.setAdapter(adapter);

        return view;
    }

    private void getOrders() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();
        ordersList = new ArrayList<>();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("ordersId");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    String orderId = orderSnapshot.getKey();
                    ordersList.add(orderId);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}