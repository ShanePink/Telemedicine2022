package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class LifeSupportActivity extends AppCompatActivity {

    String[] type={"Ventilators", "Incubator", "Anaesthetic Machine", "Heart-lung Machine", "ECMO",
            "Dialysis Machine"};
    String[] brand={"Antech", "Avante", "Becton Dickinson", "Dräger", "Fisher & Paykel",
            "Maquet (Getinge)", "GE Healthcare", "Hamilton", "LivaNova", "Medtronic", "Mindray",
            "Penlon", "Philips", "Smiths", "Sorin", "Terumo Medical", "Thermo Scientific"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_support);

        // Setting the tile page
        setTitle("Life Support Equipment");

        Spinner dropdown1=(Spinner) findViewById(R.id.chooseType);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, type);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown1.setAdapter(adapter1);

        Spinner dropdown2=(Spinner) findViewById(R.id.chooseBrand);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, brand);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown2.setAdapter(adapter2);

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start,
                                       int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(charSequence.charAt(i)))
                    {
                        return "";
                    }
                }
                return null;
            }
        };

        Button submitButton = (Button) findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Instantiate destination fragment
                        Log.d("HomeFrag","Button Pressed!");

                        startActivity(new Intent(LifeSupportActivity.this,
                                MainActivity.class));
 /*                     /*Fragment mFragment = new DiagnosticFragment();

                        // Copy this to switch page, but mfragment to desired fragment obj
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, mFragment ).commit();*/
                    }
                }
        );

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