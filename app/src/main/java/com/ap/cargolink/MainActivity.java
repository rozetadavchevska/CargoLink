package com.ap.cargolink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ap.cargolink.ui.activities.DriverActivity;
import com.ap.cargolink.ui.activities.LoginActivity;
import com.ap.cargolink.ui.activities.SenderActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        new Handler().postDelayed(() -> {
            if(user == null){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                String uid = user.getUid();
                switchViews(uid);
            }
        }, SPLASH_DELAY);
    }

    private void switchViews(String userId){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userType = snapshot.child("userType").getValue(String.class);
                    switch (userType) {
                        case "Sender":
                            startActivity(new Intent(MainActivity.this, SenderActivity.class));
                            break;
                        case "Driver":
                            startActivity(new Intent(MainActivity.this, DriverActivity.class));
                            break;
                    }
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "User type not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error accessing view", Toast.LENGTH_SHORT).show();
            }
        });
    }
}