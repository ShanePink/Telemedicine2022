package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class DiagnosticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        setTitle("Diagnostic");


        // Button
        Button btn = findViewById(R.id.routeBtn);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // instantiate ur destination fragment
                        Log.d("Diagnostic Activity","Button Pressed!");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        // passsing some values
                        intent.putExtra("BackValue", "This has back");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }
}