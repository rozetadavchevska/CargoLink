package com.ap.cargolink.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ap.cargolink.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        Button loginBtn = findViewById(R.id.loginBtn);
        Button registerBtn = findViewById(R.id.registerBtn);

        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            loginUser(emailText, passwordText);
        });

        registerBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser(String email, String password){

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();
                    switchViews(uid);

                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(authResult -> Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show());
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
                            startActivity(new Intent(LoginActivity.this, SenderActivity.class));
                            break;
                        case "Driver":
                            startActivity(new Intent(LoginActivity.this, DriverActivity.class));
                            break;
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "User type not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error accessing view", Toast.LENGTH_SHORT).show();
            }
        });
    }
}