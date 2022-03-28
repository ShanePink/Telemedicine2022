package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide Action Bar
        getSupportActionBar().hide();

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Do Login Details Verification Here

                        startActivity(new Intent(LoginActivity.this,
                                MainActivity.class));
                        finish();
                    }
                }
        );

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(LoginActivity.this,
                                SelectionTabActivity.class));
                        finish();
                    }
                }
        );
    }
}