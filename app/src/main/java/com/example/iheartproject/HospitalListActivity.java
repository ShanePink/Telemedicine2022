package com.example.iheartproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HospitalListActivity extends AppCompatActivity {

    String[] type={"Ultrasound", "MRI", "PET Scanner", "CT Scanner", "X-Ray Machine"};
    String[] brand={"GE Healthcare", "Philips", "Siemens", "Toshiba (Canon Medical)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        setTitle("Diagnostic Equipment");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}