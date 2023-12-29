package com.ap.cargolink.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ap.cargolink.ui.activities.DriverActivity;
import com.ap.cargolink.ui.activities.SenderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        String targetActivityName = intent.getStringExtra("target_activity");

        fetchUserTypeFromFirebase(context, (userType) -> {
            try {
                Class<?> targetActivity = Class.forName(targetActivityName);

                if (TextUtils.equals(userType, "sender") && targetActivity == DriverActivity.class) {
                    NotificationHelper.showNotification(context, title, message, targetActivity);
                } else if (TextUtils.equals(userType, "driver") && targetActivity == SenderActivity.class) {
                    NotificationHelper.showNotification(context, title, message, targetActivity);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public interface UserTypeCallback {
        void onUserTypeFetched(String userType);
    }

    public static void fetchUserTypeFromFirebase(Context context, UserTypeCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userType = snapshot.child("userType").getValue(String.class);
                        if (userType != null) {
                            callback.onUserTypeFetched(userType);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }
}
