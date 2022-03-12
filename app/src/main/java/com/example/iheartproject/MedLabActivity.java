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

public class MedLabActivity extends AppCompatActivity {

    String[] type={"Microscopes", "Hematology Analyzers", "Blood Gas Analysers", "Autoclaves",
            "Urinalysis Analyzers", "DNA Analyzers"};
    String[] brand={"Abbott", "AmScope", "Beckman", "Benchmark", "Bio-Rad", "Illumina", "Leica",
            "Nikon", "Mindray", "Nihon Kohden", "Olympus", "Omax", "Roche", "Siemens",
            "Thermo Fisher", "Tuttnauer", "Zeiss", "Zirbus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_lab);
        setTitle("Medical Laboratory Equipment");

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

                        startActivity(new Intent(MedLabActivity.this,
                                MainActivity.class));
 /*                     /*Fragment mFragment = new DiagnosticFragment();

                        // Copy this to switch page, but mfragment to desired fragment obj
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, mFragment ).commit();*/
                    }
                }
        );
    }
}