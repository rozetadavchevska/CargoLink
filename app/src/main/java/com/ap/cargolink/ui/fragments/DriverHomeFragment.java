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
import android.widget.TextView;

import com.ap.cargolink.R;
import com.ap.cargolink.data.adapters.DriverHomeAdapter;
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

public class DriverHomeFragment extends Fragment {
    private List<Order> recentOrdersList;
    private DriverHomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_home, container, false);

        TextView userName = view.findViewById(R.id.userName);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        getUserName(currentUserId, userName);

        FragmentManager fragmentManager = getParentFragmentManager();
        RecyclerView recentOrders = view.findViewById(R.id.recentOrders);
        recentOrdersList = new ArrayList<>();

        getRecentOrders();
        adapter = new DriverHomeAdapter(recentOrdersList, fragmentManager);
        recentOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        recentOrders.setAdapter(adapter);

        return view;
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

    private void getRecentOrders(){
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Order recentOrder = dataSnapshot.getValue(Order.class);
                    String orderStatus = dataSnapshot.child("orderStatus").getValue(String.class);
                    if("Awaiting driver".equals(orderStatus)){
                        recentOrdersList.add(recentOrder);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}