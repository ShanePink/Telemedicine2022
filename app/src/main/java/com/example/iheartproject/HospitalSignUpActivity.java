package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HospitalSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_sign_up);

        // Hide Action Bar
        getSupportActionBar().hide();

        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Do Login Details Verification Here

                        startActivity(new Intent(HospitalSignUpActivity.this,
                                LoginActivity.class));
                        finish();
                    }
                }
        );
    }
}