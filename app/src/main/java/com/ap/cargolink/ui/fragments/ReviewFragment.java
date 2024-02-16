package com.ap.cargolink.ui.fragments;

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
import com.ap.cargolink.data.models.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewFragment extends Fragment {
    String driverId, offerId, orderId;
    TextView driverName;
    public static ReviewFragment newInstance(String driversId, String offersId, String ordersId) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString("driverId", driversId);
        args.putString("offerId", offersId);
        args.putString("orderId", ordersId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        ImageButton backBtn = view.findViewById(R.id.leaveReviewBack);
        backBtn.setOnClickListener(v -> {
            FragmentManager fragment = getParentFragmentManager();
            fragment.popBackStack();
        });

        Button leaveReviewBtn = view.findViewById(R.id.leaveReviewBtn);
        leaveReviewBtn.setOnClickListener(v -> leaveReview());

        driverName = view.findViewById(R.id.driverName);

        Bundle args = getArguments();
        if(args != null){
            driverId = args.getString("driverId");
            offerId = args.getString("offersId");
            orderId = args.getString("ordersId");

            retrieveDriverName(driverId);
        }

        return view;
    }

    private void leaveReview() {
        EditText ratingNumbEditText = getView().findViewById(R.id.ratingNumb);
        String reviewText = ratingNumbEditText.getText().toString();

        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference("reviews");
        String reviewId = reviewsRef.push().getKey();

        Review review = new Review(reviewId, reviewText, driverId, orderId, offerId);
        reviewsRef.child(reviewId).setValue(review);
        Toast.makeText(getContext(), "Successfully left a review", Toast.LENGTH_LONG).show();

        getParentFragmentManager().popBackStack();
    }

    private void retrieveDriverName(String driverId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(driverId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);
                    String fullName = firstName + " " + lastName;
                    driverName.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}