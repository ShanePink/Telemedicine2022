package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class DiagnosticActivity extends AppCompatActivity {

    String[] type={"Ultrasound", "MRI", "PET Scanner", "CT Scanner", "X-Ray Machine"};
    String[] brand={"GE Healthcare", "Philips", "Siemens", "Toshiba (Canon Medical)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        setTitle("Diagnostic Equipment");

        // Button
        /*Button btn = findViewById(R.id.routeBtn);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // instantiate ur destination fragment
                        Log.d("Diagnostic Activity","Button Pressed!");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        // passing some values
                        intent.putExtra("BackValue", "This has back");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
        );*/

        // Spinner
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

                        startActivity(new Intent(DiagnosticActivity.this,
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