package com.ap.cargolink.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ap.cargolink.R;
import com.ap.cargolink.data.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private RadioGroup userTypeGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstNameUser);
        lastName = findViewById(R.id.lastNameUser);
        email = findViewById(R.id.emailUser);
        password = findViewById(R.id.passwordUser);
        userTypeGroup = findViewById(R.id.userTypeGroup);

        Button addUser = findViewById(R.id.addUserBtn);
        addUser.setOnClickListener(v -> addUser());

    }

    private void addUser(){
        String fNameUser = firstName.getText().toString();
        String lNameUser = lastName.getText().toString();
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        String userType =  getUserType();

        if (fNameUser.isEmpty() || lNameUser.isEmpty() || emailUser.isEmpty() || passwordUser.isEmpty() || userType.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        addUserToDatabase(fNameUser,lNameUser,emailUser,passwordUser,userType);
    }

    private String getUserType(){
        int selectedBtnId = userTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedBtn = findViewById(selectedBtnId);

        if(selectedBtn != null){
            return selectedBtn.getText().toString();
        }

        return "";
    }

    private void addUserToDatabase(String fName, String lName, String emailUser, String passwordUser, String userType){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String uid = auth.getCurrentUser().getUid();
                        User addNewUser = new User(uid, emailUser, passwordUser, fName, lName, userType, new HashMap<>());
                        usersRef.child(uid).setValue(addNewUser)
                                .addOnSuccessListener(e -> {
                                    Toast.makeText(this,"Successfully added user", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, LoginActivity.class));
                                })
                                .addOnFailureListener(e -> Toast.makeText(this,"Error adding user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
    }

}