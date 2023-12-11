package com.ap.cargolink.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ap.cargolink.R;
import com.ap.cargolink.ui.fragments.DriverHomeFragment;
import com.ap.cargolink.ui.fragments.DriverOrdersFragment;
import com.ap.cargolink.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String currentUserId;
    BottomNavigationView bottomNav;
    DriverHomeFragment driverHomeFragment = new DriverHomeFragment();
    DriverOrdersFragment driverOrdersFragment = new DriverOrdersFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.driverFrameLayout, driverHomeFragment).commit();

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        currentUserId = currentUser.getUid();

        bottomNav = findViewById(R.id.driverBottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.driverFrameLayout, driverHomeFragment).commit();
                return true;
            } else if (itemId == R.id.orders) {
                getSupportFragmentManager().beginTransaction().replace(R.id.driverFrameLayout, driverOrdersFragment).commit();
                return true;
            } else if (itemId == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.driverFrameLayout, profileFragment).commit();
                return true;
            }

            return false;
        });
    }
}