package com.example.iheartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO: Learn about how to implement firebase and how to use an Object  (very important)
    // This is the Object u would need to create, similar to a database table
    // Place your value inside, eg name, item, model those
    class MedicalEquipment{
        int id;
        String equipmentType;
    }

    public MedicalEquipment SetMedicalEquipment(String equipmentType){
        MedicalEquipment med = new MedicalEquipment();
        med.id = 1;
        med.equipmentType = equipmentType;
        return med;
    }

    class Data{
        String id;
        String operator;
        SampleObject obj;
    }
    // U can do like a nested object, so u can categorize into one object based on ur type
    class SampleObject{
        String qty;
        String expDate;
        String equipmentName;
        String manufacturer;
        String model;
        String serialNo;
    }

    // Sample Object, to test if we can put value inside the object
    public Data InstantiateObj(){
        Data data = new Data();
        SampleObject sampleObj = new SampleObject();
        sampleObj.qty="Quantity";
        sampleObj.expDate="Exp Date";
        sampleObj.equipmentName="Equipment Name";
        sampleObj.manufacturer="Manufacturer";
        sampleObj.model="Model";
        sampleObj.serialNo="Serial Number";
        data.id = "1";
        data.operator = "Same level";
        data.obj = sampleObj;

        return data;
    }

    public String currentUserEmail;
    public String currentUserFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide Action Bar
        getSupportActionBar().hide();


        // Invoke this in main, as it will be used globally
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        NavigationUI.setupWithNavController(bottomNav, navController);


        currentUserEmail = getIntent().getStringExtra("userEmail");
        if(currentUserEmail == null)
        {
            Log.d("Main","No email found");
        }
        else
        {
            Log.d("Main",currentUserEmail);
            // Perform any action here
        }
        currentUserFullName = getIntent().getStringExtra("userFullName");
        if(currentUserFullName == null)
        {
            Log.d("Main","No email found");
        }
        else
        {
            Log.d("Main",currentUserFullName);
            // Perform any action here
        }


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Item");

        // TODO : Please learn about Object Reference by value or pointer
        // REMEMBER IT WILL BE CLONED!!!
        // Currently the object right now would use the same object
        // But i think since u testing so is fine can ignore, test for me
        Data data = InstantiateObj();
        List<Data> dataList = new ArrayList<>();
        for(int i = 0 ; i < 10; i++){
            String value = String.valueOf(i+2);
            Data newData = data;
            newData.id = value;

            // Add the object into the list
            dataList.add(newData);
        }

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // TODO: Learn about Json format
                // Fetch ur data, from the dataSnapshot (we set string, so is a json form)
                String value = dataSnapshot.getValue(String.class);

                // We log it so u know what happens
                Log.d("Test", "Value is: " + value);

                // We get the type of the Object, which is a list of object (can copy)
                // This is quite important, as u fetch the info d but u need to parse it from json to a more
                // bite size object for ur app to use (which is list of object)
                Type listType = new TypeToken<ArrayList<Data>>(){}.getType();

                // TODO: GSON implementation (good for u)
                // Just copy this, it basically parse ur Json string into the object type u want
                List<Data> firebaseDataList = new Gson().fromJson(value, listType);

                // We log it to see what happen
                // TODO : Populate ur UI with these info
//                for( Data fireBaseData : firebaseDataList ) {
//                    Log.d("Test", "Id is: " + fireBaseData.id);
//                    Log.d("Test", "Operator is: " + fireBaseData.operator);
//                    Log.d("Test", "com.example.iheartproject.Equipment Name is: " + fireBaseData.obj);
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
                // TODO: Place a snackbar here said have issue on firebase, log it too
                Snackbar snackbar2 = Snackbar.make(getWindow().getDecorView(),
                        "There was a problem on database.", Snackbar.LENGTH_SHORT);
                snackbar2.show();
            }
        });

        //  Step 5 : Remember ur object list right? u can alter ur object value d, then convert back
        // to json string format, so u can store into ur firebase DB
        String dataJson = new Gson().toJson(dataList);

        // Step 6 : This is for u to save ur json string to database
        // Best to put like a try catch to make sure ur database no problem
        // TODO : Learn how to try catch, and test if onCancelled would handle the setValue error
        myRef.setValue(dataJson);





        DatabaseReference reference = database.getReference();
        // Connecting it to the firebase authentication database
        FirebaseAuth tmAuth = FirebaseAuth.getInstance();
        FirebaseUser user = tmAuth.getCurrentUser();
        reference.child("users").child("test").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    // If not successful, we stop the process
                    Log.e("firebase", "Error getting data.", task.getException());
                    return;
                } else {
                    // If successful, we parse the value
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    List<User> userList = new ArrayList<>();
                    User currentUser = null;
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        // Parse the whole row of firebase data into our object, and add into list
                        User userObj = ds.getValue(User.class);
                        userList.add(userObj);
                    }

                    // Loop again to match user
                    for (User userObj : userList) {
                        // Try to match if user info is same as firebase realtime
                        if (user.getEmail().equals(userObj.Email)) {
                            Log.d("firebase", "User email match.");
                            currentUser = userObj;
                            break;
                        }
                    }

                    // Check if user exist
                    if (currentUser == null) {
                        Log.e("firebase", "Unable to find user info into firebase.");
                        return;
                    } else {
                        if (currentUser.isHospital) {
                            CardView diagnosticCV =  (CardView) findViewById(R.id.DiagnosticButton);
                            diagnosticCV.setVisibility(View.GONE);

                            CardView treatmentCV =  (CardView) findViewById(R.id.TreatmentButton);
                            treatmentCV.setVisibility(View.GONE);

                            CardView lifeSupportCV =  (CardView) findViewById(R.id.LifeSupportButton);
                            lifeSupportCV.setVisibility(View.GONE);

                            CardView monitorCV =  (CardView) findViewById(R.id.MonitorButton);
                            monitorCV.setVisibility(View.GONE);

                            CardView medLabCV =  (CardView) findViewById(R.id.MedLabButton);
                            medLabCV.setVisibility(View.GONE);

                            CardView therapeuticCV =  (CardView) findViewById(R.id.TherapeuticButton);
                            therapeuticCV.setVisibility(View.GONE);

                            TextView showItemNum = (TextView) findViewById(R.id.numOfSelectionDonor);
                            showItemNum.setVisibility(View.GONE);

                            CardView hospMedReqCV = (CardView) findViewById(R.id.HospItemReqButton);
                            hospMedReqCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d("HomeFrag", "Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent(MainActivity.this, HospitalHomeActivity.class);
                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);
                                    newIntent.putExtra("userFullName", currentUserFullName);

                                    startActivity(newIntent);
                                }
                            });
                        } else {
                            CardView hospMedReqCV = (CardView) findViewById(R.id.HospItemReqButton);
                            hospMedReqCV.setVisibility(View.GONE);

                            TextView showItemNum = (TextView) findViewById(R.id.numOfSelectionHosp);
                            showItemNum.setVisibility(View.GONE);

                            CardView diagnosticCV =  (CardView) findViewById(R.id.DiagnosticButton);
                            diagnosticCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, DiagnosticActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                            CardView treatmentCV =  (CardView) findViewById(R.id.TreatmentButton);
                            treatmentCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, TreatmentActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                            CardView lifeSupportCV =  (CardView) findViewById(R.id.LifeSupportButton);
                            lifeSupportCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, LifeSupportActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                            CardView monitorCV =  (CardView) findViewById(R.id.MonitorButton);
                            monitorCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, MonitorActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                            CardView medLabCV =  (CardView) findViewById(R.id.MedLabButton);
                            medLabCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, MedLabActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                            CardView therapeuticCV =  (CardView) findViewById(R.id.TherapeuticButton);
                            therapeuticCV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Instantiate destination fragment
                                    Log.d("HomeFrag","Button Pressed!");

                                    // TODO : Remember this, copy this and change the intent from and to activities
                                    // This section is for u to
                                    // Redirect user to main activity class
                                    Intent newIntent = new Intent( MainActivity.this, TherapeuticActivity.class);

                                    // Assign ur information to new intent, with a key
                                    newIntent.putExtra("userEmail", currentUserEmail);

                                    startActivity(newIntent);

                                    /*Fragment mFragment = new DiagnosticFragment();

                                    // Copy this to switch page, but mfragment to desired fragment obj
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_fragment, mFragment ).commit();*/
                                }
                            });

                        }
                    }
                }
            }
        });



        return;
    }
}
