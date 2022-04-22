package com.example.iheartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HospitalRequestActivity extends AppCompatActivity {

    HospEquipmentListAdapter adapter;
    ListView lv;
    ArrayList<EquipmentItem> eqpList = new ArrayList<EquipmentItem>();
    String userEmail;
    String userFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_request);

        setTitle("Equipment Status");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Fetch intent info
        userEmail = getIntent().getStringExtra("userEmail");
        if (userEmail == null)
        {
            Log.d("HospHome","No email found");
        }
        else
        {
            if(userEmail == null)
            {
                Log.d("HospHome","No email found");
            }
            else
                Log.d("HospHome",userEmail);
            // Perform any action here
        }
        // Fetch intent info
        userFullName = getIntent().getStringExtra("userFullName");
        if (userFullName == null)
        {
            Log.d("HospHome","No email found");
        }
        else
        {
            if(userFullName == null)
            {
                Log.d("HospHome","No fullname found");
            }
            else
                Log.d("HospHome",userFullName);
            // Perform any action here
        }

        // DATABASE
        // Connecting it to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://telemedicine2022-2137d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        // CAN COPY IF YOU NEED TO QUERY THE INFO
        // We query to our realtime database, and acquire our additional info
        myRef.child("MedicalEquipment").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task2) {
                if (!task2.isSuccessful()) {
                    // If not successful, we stop the process
                    Log.e("firebase", "Error getting data", task2.getException());
                }
                else {
                    // If successful, we parse the value
                    Log.d("firebase", String.valueOf(task2.getResult().getValue()));

                    // TODO: Rework on diagnostic and other activity, can implement as such
                    // Basically, we get the child of the data one by one, parse and insert to our object list
                    for (DataSnapshot ds : task2.getResult().getChildren()) {
                        // If u want a single value, u can get it like this via variable name
                        // Parse the whole row of firebase data into our object, and add into list
                        EquipmentItem eqp = ds.getValue(EquipmentItem.class);

                        // Add Equipment Donate Status if not donated
                        if(!eqp.getDonateStatus())
                            eqpList.add(eqp);

                        // Do what you want with the list
                        Log.d("firebase", eqp.getSerialNo());
                    }

                    lv = (ListView) findViewById(R.id.eqpList);
                    adapter = new HospEquipmentListAdapter(eqpList, getApplicationContext());

                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView parent, View view, int position, long id) {

                            EquipmentItem dataModel= eqpList.get(position);
                            Log.d("HRA", dataModel.getSerialNo()+ " Selected");
                            dataModel.setDonateStatus(!dataModel.getDonateStatus());
                            adapter.notifyDataSetChanged();


                        }
                    });
                }
            }
        });



        Button btn = (Button) findViewById(R.id.requestButton);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Instantiate destination fragment
                        Log.d("HRA","Button Pressed!");


                        // Get the checked medical equipments
                        if(adapter == null)
                        {
                            Toast.makeText(HospitalRequestActivity.this, "There is nothing to be requested",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ArrayList<EquipmentItem> chkEqpList = adapter.getAllCheckedItem();


                        myRef.child("MedicalEquipment").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task2) {
                                if (!task2.isSuccessful()) {
                                    // If not successful, we stop the process
                                    Log.e("firebase", "Error getting data", task2.getException());
                                }
                                else {
                                    // If successful, we parse the value
                                    Map<String, Object> values = new HashMap<>();
                                    for(EquipmentItem chkEqp : chkEqpList) {
                                        for (DataSnapshot ds : task2.getResult().getChildren()) {
                                            // If u want a single value, u can get it like this via variable name
                                            // Parse the whole row of firebase data into our object, and add into list

                                            EquipmentItem eqp = ds.getValue(EquipmentItem.class);

                                            if(chkEqp.getUid().equals(eqp.getUid()))
                                            {
                                                // Get the key of the Checked Equipment List and update it
                                                String key = ds.getKey();
                                                myRef.child("MedicalEquipment").child(key).setValue(chkEqp)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("firebase", ds.getKey() + " has been updated");
                                                                // Send Email here

                                                                String content = String.format("Dear donor, \n\nEquipment : %s \nType : %s \nBrand : %s \n" +
                                                                                "Model : %s  \nSerial No : %s \n\n" +
                                                                        "This item has been donated successfully to %s.\n " +
                                                                                "Please contact %s for handover session. \n Thank you."
                                                                        ,chkEqp.getEquipment().toString(), chkEqp.getEquipmentType(), chkEqp.getBrand(), chkEqp.getModel(), chkEqp.getSerialNo(), userFullName, userEmail);
                                                                SendEmail(chkEqp.getDonorEmail(), content);
                                                                Intent newIntent = new Intent(HospitalRequestActivity.this, MainActivity.class);
                                                                startActivity(newIntent);
                                                                finish();

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e("firebase", "Unable to update user info in firebase.");
                                                                finish();
                                                            }
                                                        });
                                            }
                                        }

                                    }
                                    }


                                Toast.makeText(HospitalRequestActivity.this, "Medical equipment requested successfully",
                                        Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });


                    }
                }
        );



    }

    public void SendEmail(String receipientEmail, String content){

        try {
            Log.i("Send email", "");

            // TODO : Change this to ur emails
            String stringSenderEmail = "iheartapp22@gmail.com";
            String stringPasswordSenderEmail = "iheart2022";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receipientEmail));
            mimeMessage.setSubject("Telemedicine Medical Equipment Donate Status" );
            mimeMessage.setText(content);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            Log.i("Hospital Request", "Finished sending email...");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {

            e.printStackTrace();
        }
    }

}
