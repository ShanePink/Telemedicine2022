package com.example.iheartproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LifeSupportActivity extends AppCompatActivity {

    EquipmentObj[] equipments = {
            new EquipmentObj("Getinge", "Ventilators"),
            new EquipmentObj("Hamilton", "Ventilators"),
            new EquipmentObj("Dräger", "Ventilators"),
            new EquipmentObj("Mindray", "Ultrasound"),
            new EquipmentObj("Medtronic", "Ventilators"),
            new EquipmentObj("GE", "Ventilators"),
            new EquipmentObj("Philips", "Ventilators"),
            new EquipmentObj("Smiths", "Ventilators"),
            new EquipmentObj("Becton Dickinson", "Ventilators"),
            new EquipmentObj("Fisher & paykel", "Ventilators"),
            new EquipmentObj("Dräger", "Incubator"),

            new EquipmentObj("Avante GE", "Anaesthetic Machine"),
            new EquipmentObj("Dräger", "Anaesthetic Machine"),
            new EquipmentObj("Maquet", "Anaesthetic Machine"),
            new EquipmentObj("Penlon", "Anaesthetic Machine"),
            new EquipmentObj("Mindray", "Anaesthetic Machine"),

            new EquipmentObj("Terumo", "Heart Lung Machine"),
            new EquipmentObj("LivaNova", "Heart Lung Machine"),
            new EquipmentObj("Getinge", "Heart Lung Machine"),
            new EquipmentObj("Medtronic", "Heart Lung Machine"),
            new EquipmentObj("Maquet", "Heart Lung Machine"),

            new EquipmentObj("Getinge", "ECMO"),
            new EquipmentObj("LivaNova", "ECMO"),
            new EquipmentObj("Medtronic ", "ECMO"),
            new EquipmentObj("Sorin", "ECMO"),
            new EquipmentObj("Terumo", "ECMO"),

            new EquipmentObj("Baxter", "Dialysis Machine"),
            new EquipmentObj("Fresenius", "Dialysis Machine"),
            new EquipmentObj("B braun", "Dialysis Machine"),
            new EquipmentObj("Medtronic ", "Dialysis Machine"),
    };
    String[] type= EquipmentObj.convertEquipmentListtoString(equipments);
    String[] brand= {};

    EditText expDateTbx;
    DatePickerDialog picker;
    EditText typeModelTbx;
    EditText serialNoTbx;
    EditText qtyTbx;

    String modelName;
    String serialNo;
    Integer quantity;
    String manufacturerBrand;
    String lifeSupportType;
    String currentExpiryDate;

    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_support);

        // Setting the tile page
        setTitle("Life Support Equipment");

        // Fetch intent info
        String userEmailJson = getIntent().getStringExtra("userEmail");
        if (userEmailJson == null)
        {
            Log.d("Life Support", "No email found.");
        }
        else
        {
            // Convert the json to plain string
            userEmail = new Gson().fromJson(userEmailJson, String.class);
            if (userEmail == null)
            {
                Log.d("Life Support", "No email found.");
            }
            else
            {
                Log.d("Life Support", userEmail);
            }
        }

        // DATABASE
        // Connecting it to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        // Assign all the button with id
        typeModelTbx = (EditText) findViewById(R.id.typeModel);
        qtyTbx = (EditText) findViewById(R.id.typeQuantity);
        serialNoTbx = (EditText) findViewById(R.id.typeSerialNum);
        expDateTbx = (EditText) findViewById(R.id.typeDate);

        expDateTbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LifeSupportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                expDateTbx.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                //set expiry date for at least a year, so disabled dates
                picker.getDatePicker().setMinDate(System.currentTimeMillis() + 31556952000L);
                picker.show();
            }
        });

        Spinner dropdown1 = (Spinner) findViewById(R.id.chooseType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, type);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown1.setAdapter(adapter1);

        Spinner dropdown2 = (Spinner) findViewById(R.id.chooseBrand);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, brand);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown2.setAdapter(adapter2);

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                brand = EquipmentObj.filterEquipmentWithCategory(type[position], equipments);
                ArrayAdapter<String> newAdapter2=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, brand);
                newAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown2.setAdapter(newAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start,
                                       int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(charSequence.charAt(i))) {
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
                        Log.d("HomeFrag", "Button Pressed!");

                        // ASSIGN THE UI VALUE TO STRINGS
                        String uuid = UUID.randomUUID().toString().replace("-", "");
                        lifeSupportType = dropdown1.getSelectedItem().toString();
                        manufacturerBrand = dropdown2.getSelectedItem().toString();
                        modelName = typeModelTbx.getText().toString();
                        serialNo = serialNoTbx.getText().toString();
                        quantity = Integer.valueOf(qtyTbx.getText().toString());
                        currentExpiryDate = expDateTbx.getText().toString();

                        // CREATE OBJECT WITH IT
                        EquipmentItem equipment = new EquipmentItem(uuid, Equipment.LifeSupport, lifeSupportType,
                                manufacturerBrand, modelName, serialNo, quantity, currentExpiryDate, userEmail, false);

                        // TO update the database
                        // PUSH , get a new ref, then set/ save the value
                        DatabaseReference newRef = myRef.child("MedicalEquipment").push();
                        newRef.setValue(equipment);

                        finish();
 /*                     /*Fragment mFragment = new DiagnosticFragment();

                        // Copy this to switch page, but mfragment to desired fragment obj
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment, mFragment ).commit();*/
                    }
                }
        );

        /*// CAN COPY IF YOU NEED TO QUERY THE INFO
        // Read from the database
        myRef.child("MedicalEquipment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // FETCHING EQUIPMENT ITEM LIST FROM FIREBASE
                List<EquipmentItem> eqpList = new ArrayList<LifeSupportActivity.EquipmentItem>();

                Map<String, EquipmentItem> td = (HashMap<String, EquipmentItem>) dataSnapshot.getValue();
                List<Object> tdList = new ArrayList<Object>(td.values());
                for (Object objectTd : tdList) {
                    EquipmentItem eqpItem2 = new EquipmentItem();
                    Map<String, String> item = (Map<String, String>) objectTd;
                    for (Map.Entry<String, String> entry : item.entrySet()) {
                        String key = entry.getKey();
                        String value = String.valueOf(entry.getValue());

                        if (key.equals("com.example.iheartproject.Equipment"))
                            eqpItem2.Equipment = LifeSupportActivity.Equipment.valueOf(value);
                        if (key.equals("Qty"))
                            eqpItem2.Qty = Integer.valueOf(value);
                        if (key.equals("ExpiryDate"))
                            eqpItem2.ExpiryDate = value;
                        if (key.equals("EquipmentType"))
                            eqpItem2.EquipmentType = value;
                        if (key.equals("SerialNo"))
                            eqpItem2.SerialNo = value;
                        if (key.equals("Brand"))
                            eqpItem2.Brand = value;
                        if (key.equals("Model"))
                            eqpItem2.Model = value;
                        if (key.equals("Uid"))
                            eqpItem2.Uid = value;

                        System.out.println(key);
                        System.out.println(value);
                        // do stuff
                    }
                    eqpList.add(eqpItem2);
                }

                ArrayList<EquipmentItem> currentDonorItemList = new ArrayList<>();
                // Looping the list
                System.out.println("LIST ITEM");
                for (EquipmentItem item : eqpList) {
                    System.out.println(item.Brand);
                    System.out.println(item.Equipment);
                    System.out.println(item.DonorEmail);
                    // To acquire only the specific donor item
                    // Check if the email is same with current user email
                    if (item.DonorEmail == userEmail)
                    {
                        // Add into current donor item list
                        currentDonorItemList.add(item);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
                // TODO: Place a snackbar here said have issue on firebase, log it too
                Snackbar snackbar1 = Snackbar.make(getWindow().getDecorView(),
                        "There was a problem on database.", Snackbar.LENGTH_SHORT);
                snackbar1.show();
            }
        });*/

        // Setup a submit button listener

        // Make sure to check ur db saving status before route to other activity
        /*if(SavingDatabase() == true)
        {
            // perform routing
        }
        else
        {
            // show error message and don't route
            Snackbar snackbar2 = Snackbar.make(getWindow().getDecorView(), "Message is restored!", Snackbar.LENGTH_SHORT);
            snackbar2.show();
        }
    }*/

        // Setup values
    /*private void SetupValues(){
        // setup ur list item, clear all values
    }*/


    /*private boolean SavingDatabase(){
        boolean isDatabaseSaveCompleted = false;
        // Do your own db saving process

        // Check if success
        return isDatabaseSaveCompleted;
    }*/

        // Perform submit actions
        // Perform Database save
        // Return status of database save

    }

    ;
}