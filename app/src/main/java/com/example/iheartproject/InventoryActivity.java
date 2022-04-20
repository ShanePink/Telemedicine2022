package com.example.iheartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner$InspectionCompanion;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.UUID;


public class InventoryActivity extends AppCompatActivity {

    private Spinner equipmentTypeSpn;
    private Spinner brandSpn;
    private String chooseType;
    private String chooseBrand;
    private Button searchButton;
    private EquipmentItem equipmentInfo;
    private RecyclerView recyclerView;
    private EquipmentListAdapter equipmentListAdapter;
    private ArrayList<EquipmentItem> equipmentArray = new ArrayList<>();
    private ArrayList<EquipmentItem> filterEquipmentArray;
    String TAG = "InventoryActivity";

    public void fillEquipmentArray()
    {
        // DATABASE
        // Connecting it to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("MedicalEquipment");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    equipmentInfo = dataSnapshot.getValue(EquipmentItem.class);
                    equipmentArray.add(equipmentInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InventoryActivity.this, "Error getting data.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Filter equipmentArray and then add matched result into ArrayList filteredEquipmentArray
    public void searchEquipmentList() {
        // Create new object of Array List everytime searching to avoid loading duplicated
        // and old filtered array list
        filterEquipmentArray = new ArrayList<>();

        for (int i = 0; i < equipmentArray.size(); i++) {
            if(equipmentArray.get(i).getEquipmentType().equals(chooseType) && equipmentArray.get(i).getBrand().equals(chooseBrand)) {
                filterEquipmentArray.add(equipmentArray.get(i));
            }
        }
        equipmentListAdapter.setEquipment(filterEquipmentArray);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        setTitle("Equipment Management");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Assign all the spinner with id
        equipmentTypeSpn = (Spinner) findViewById(R.id.chooseType);
        brandSpn = (Spinner) findViewById(R.id.chooseBrand);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.equipmentList);

        fillEquipmentArray();

        // Search result in recycler view adapter operation
        equipmentListAdapter = new EquipmentListAdapter(this);
        recyclerView.setAdapter(equipmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Equipment type spinner adapter
        equipmentTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chooseType = equipmentTypeSpn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(InventoryActivity.this, "Please select the equipment.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Brand spinner adapter
        brandSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chooseBrand = brandSpn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(InventoryActivity.this, "Please select the brand.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Search button listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEquipmentList();
            }
        });
    }
}