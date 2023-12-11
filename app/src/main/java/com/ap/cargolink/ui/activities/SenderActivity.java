package com.ap.cargolink.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ap.cargolink.R;
import com.ap.cargolink.ui.fragments.ProfileFragment;
import com.ap.cargolink.ui.fragments.SenderHomeFragment;
import com.ap.cargolink.ui.fragments.SenderOrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SenderActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String currentUserId;
    BottomNavigationView bottomNav;
    SenderHomeFragment senderHomeFragment = new SenderHomeFragment();
    SenderOrdersFragment senderOrdersFragment = new SenderOrdersFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.senderFrameLayout, senderHomeFragment).commit();

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        currentUserId = currentUser.getUid();

        bottomNav = findViewById(R.id.senderBottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.senderFrameLayout, senderHomeFragment).commit();
                return true;
            } else if (itemId == R.id.orders) {
                getSupportFragmentManager().beginTransaction().replace(R.id.senderFrameLayout, senderOrdersFragment).commit();
                return true;
            } else if (itemId == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.senderFrameLayout, profileFragment).commit();
                return true;
            }

            return false;
        });
    }
}