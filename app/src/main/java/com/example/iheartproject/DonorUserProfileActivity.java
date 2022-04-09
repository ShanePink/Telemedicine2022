package com.example.iheartproject;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;

import com.example.iheartproject.UpdateUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonorUserProfileActivity extends AppCompatActivity {

    private EditText updateFullNameTbx;
    private EditText updateDonorUserNameTbx;
    private EditText updateEmailTbx;
    private String updateFullName;
    private String updateDonorUserName;
    private String updateEmail;
    String TAG = "DonorUserProfileActivity";

    public void EditUserProfile(String updateFullName, String updateDonorUserName, String updateEmail)
    {
        try
        {
            // DATABASE
            // Connecting it to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference();

            // Update a user's profile
            // Connecting it to the firebase authentication database
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(updateFullName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");

                                // Query to realtime database, and acquire additional info
                                myRef.child("users").child("test").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task1) {
                                        if (!task1.isSuccessful()) {
                                            // If not successful, stop the process
                                            Log.e("firebase", "Error getting data.", task1.getException());
                                            Toast.makeText(DonorUserProfileActivity.this, "Error occurred when connecting to firebase.",
                                                    Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else {
                                            // If successful, parse the value
                                            Log.d("firebase", String.valueOf(task1.getResult().getValue()));

                                            List<UpdateUser> userList = new ArrayList<>();
                                            User currentUser = null;
                                            // Basically, we get the child of the data one by one, parse and insert to our object list
                                            for (DataSnapshot ds: task1.getResult().getChildren()) {
                                                // Parse the whole row of firebase data into object, and add into list
                                                UpdateUser userObj = ds.getValue(UpdateUser.class);
                                                userList.add(userObj);
                                            }

                                            // Loop again to match user
                                            for (UpdateUser userObj : userList) {
                                                // Try to match if user info is same as firebase realtime
                                                if (user.getDisplayName().equals(userObj.FullName)) {
                                                    Log.d("firebase", "User full name match.");
                                                    break;
                                                }
                                            }

                                            // Check if user exist
                                            if (currentUser == null) {
                                                Log.e("firebase", "Unable to find user info in firebase.");
                                                Toast.makeText(DonorUserProfileActivity.this, "Unable to fetch user info",
                                                        Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            Toast.makeText(DonorUserProfileActivity.this, "User profile updated.",
                                                    Toast.LENGTH_SHORT).show();

                                            Intent newIntent = new Intent(DonorUserProfileActivity.this, profileFragment.class);
                                            startActivity(newIntent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                Log.d(TAG, "User profile fail to update.");
                                Toast.makeText(DonorUserProfileActivity.this, "User profile fail to update.",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        }
        catch (Exception ex)
        {
            Log.e(TAG, "updateWithEmail:failure" + ex.getMessage());
            Toast.makeText(DonorUserProfileActivity.this, "Unexpected error occurred during edit user profile: " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_user_profile);

        // Title in action bar
        setTitle("Edit User Profile");

        // Show back button in action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener((view -> {
            Intent i = new Intent(view.getContext(), LoginActivity.class);
            i.putExtra("fullName", "tester10");
            startActivity(i);
        }));

        Intent data = getIntent();
        String fullName = data.getStringExtra("fullName");

        Log.d(TAG, "onCreate: " + fullName + " ");


    }

    /*public void updateProfile() {
        // [START update_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
        // [END update_profile]
    }*/


}




    /*private void EditProfile(String updateFullName, String updateDonorUserName, String updateEmail, DatabaseReference myRef) {
        try {
            //Check if user key in identical full name, user name and email
            if (updateFullName.equals(fullName)) {
                Toast.makeText(DonorUserProfileActivity.this, "User Full Name is same as existing Full Name. Please try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (updateDonorUserName.equals(donorUserName)) {
                Toast.makeText(DonorUserProfileActivity.this, "Username is same as existing Username. Please try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (updateEmail.equals(email)) {
                Toast.makeText(DonorUserProfileActivity.this, "User Email is same as existing Email. Please try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }*//*

            // Update a user's profile
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(updateFullName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            } else {
                                Log.d(TAG, "User profile fail to update.");
                                Toast.makeText(DonorUserProfileActivity.this, "User profile fail to update.",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

            // Set a user's email address
            user.updateEmail("user@example.com")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                            }
                        }
                    });
        } catch (Exception ex) {
            Log.w(TAG, "updateUserWithEmail:failure", ex);
            Toast.makeText(DonorUserProfileActivity.this, "Exception error occurred.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }*/


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_user_profile);

        // Title in action bar
        setTitle("Edit User Profile");

        // Show back button in action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Assign text box components
        updateFullNameTbx = (EditText) findViewById(R.id.updateFullName);
        updateDonorUserNameTbx = (EditText) findViewById(R.id.updateDonorUserName);
        updateEmailTbx = (EditText) findViewById(R.id.updateEmail);

        // DATABASE
        // Connecting it to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //  Assign the buttons texts
                        updateFullName = updateFullNameTbx.getText().toString();
                        updateDonorUserName = updateDonorUserNameTbx.getText().toString();
                        updateEmail = updateEmailTbx.getText().toString();

                        // Do Login Details Verification Here
                        EditProfile(updateFullName, updateDonorUserName, updateEmail, myRef);
                    }
                }
        );

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
                // TODO: Place a snackbar here said have issue on firebase, log it too
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView(),
                        "There was a problem on database.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}*/






