package com.example.iheartproject;

import android.annotation.SuppressLint;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TermAndConditionActivity extends AppCompatActivity {

    TextView text;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        setTitle("Terms of Service");

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        text = findViewById(R.id.terms_condition_textview);
        text.setText("PLEASE READ THESE PRIVACY POLICY CAREFULLY BEFORE USING THIS MOBILE APPLICATION. THIS MOBILE APPLICATION IS DEVELOPED TO PROVIDE A PLATFORM FOR HOSPITALS IN NEED TO RAISE THEIR REQUEST AND DONORS TO ANNOUNCE THE RESOURCES THEY ARE PREPARING TO DONATE. THIS MOBILE APPLICATION IS OWNED AND OPERATED BY MY CHARITY, INC. THIS IS A LEGALLY BINDING AGREEMENT BETWEEN YOU AS AN END USER AND MY CHARITY, INC. THE COLLECTION OF PERSONAL INFORMATION FROM END USER IS ALIGN WITH PERSONAL DATA PROTECTION ACT 2010 (ACT 709)." +
                "PERSONAL INFORMATION\n" +
                "THIS MOBILE APPLICATION WILL NOT RECORD ANY PERSONAL INFORMATION FROM END USER EXCEPT WITH THE AGREEMENT AND PERMISSION PROVIDED BY THE END USER. ALL INFORMATION IS NOT SHARED WITH OTHER ORGANIZATIONS FOR OTHER PURPOSE.\n" +
                "DATA COLLECTION\n" +
                "THIS MOBILE APPLICATION COLLECT DATA WITH THE CONSENT OF THE END USER AND IS DONE UNDER VOLUNTARILY BASIS, BUT NOT LIMITED TO REGISTRATION PROCESS. THE DATA COLLECTION FOR USER REGISTRATION ARE NEEDED ACCORDING TO THE USE OF FUNCTIONS. THE PERSONAL INFORMATION THAT ARE COLLECTED FROM THE END USER ARE LISTED AS FOLLOW\n" +
                "i.\tNAME\n" +
                "ii.\tEMAIL ADDRESS\n" +
                "THE PERSONAL INFORMATION COLLECTED WILL NOT USED FOR OTHER PURPOSE OR SHARED WITH ANY THIRD PARTY EXCEPT WITH THE PERMISSION OR AGREEMENT FROM END USER. IF THE PERSONAL INFORMATION IS  INSUFFICIENT, END USER UNABLE TO REGISTER AND THE REQUEST MAY NOT BE ACCEPTED. THIS MOBILE APPLICATION INCLUDED SECURITY FEATURES WHICH ADHERES TO GLOBAL STANDARDS TO PROTECT THE PRIVACY AND SECURITY OF THE DATA COLLECTED FROM END USER. IF THERE ARE ANY CHANGES OR MODIFICATIONS MADE IN THIS PRIVACY POLICY, IT WILL BE ANNOUNCED AND UPDATED ON THIS USER INTERFACE.\n");
        String[] para = text.getText().toString().split("\r\n\r\n");


    }
}