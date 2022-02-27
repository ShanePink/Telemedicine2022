package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class LifeSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_support);

        // Setting the tile page
        setTitle("Life Support Equipment");

        // Setup a submit button listener

        // Make sure to check ur db saving status before route to other activity
        if(SavingDatabase() == true)
        {
            // perform routing
        }
        else
        {
            // show error message and dont route
            Snackbar snackbar1 = Snackbar.make(getWindow().getDecorView(), "Message is restored!", Snackbar.LENGTH_SHORT);
            snackbar1.show();
        }
    }

    // Setup values
    private void SetupValues(){
        // setup ur list item, clear all values
    }


    private boolean SavingDatabase(){
        boolean isDatabaseSaveCompleted = false;
        // Do your own db saving process

        // Check if success
        return isDatabaseSaveCompleted;
    }

    // Perform submit actions
        // Perform Database save
            // Return status of database save

}