package com.example.iheartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HospitalHomeActivity extends AppCompatActivity {
    List<User> userList = new ArrayList<>();
    String userEmail;
    String userFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_home);

        setTitle("Write a Message");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Fetch intent info
        String userEmailJson = getIntent().getStringExtra("userEmail");
        if (userEmailJson == null)
        {
            Log.d("HospHome","No email found");
        }
        else
        {
            // Convert the json to plain string
            userEmail = new Gson().fromJson(userEmailJson,String.class);
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

        myRef.child("users").child("test").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                        String email = ds.child("Email").getValue(String.class);

                        // Parse the whole row of firebase data into our object, and add into list
                        User user = ds.getValue(User.class);
                        if(!user.isHospital)
                            userList.add(user);
                        // Do what you want with the list
                        Log.d("firebase", user.FullName);
                        Log.d("firebase", String.valueOf(user.isHospital));
                        Log.d("firebase", user.Email);
                    }
                }
            }
        });


        EditText msgTbx = (EditText) findViewById(R.id.messageTextBox);

        Button sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = "Hi! I would like to request some medical equipments such as : \n" + msgTbx.getText().toString() + "\nThank you.";
                SendEmail(msgContent);
            }
        });
    }


    public void SendEmail(String content){

        try {
            Log.i("Send email", "");
            ArrayList<String> toEmailList = new ArrayList<>();
            for(User user : userList)
            {
                toEmailList.add(user.Email);
            }
            String[] receiverEmails = toEmailList.toArray(new String[toEmailList.size()]);

            // TODO : Change this to ur emails
            String stringSenderEmail = "eunicetan953@gmail.com";
            String stringPasswordSenderEmail = "eunicetan123456";

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
            for (String recipientEmail : receiverEmails){
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            }
            mimeMessage.setSubject("Telemedicine Medical Equipment Request : " + userFullName);
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
            Toast.makeText(HospitalHomeActivity.this,
                    "Email sent.", Toast.LENGTH_SHORT).show();
            finish();
            Log.i("Hospital Home", "Finished sending email...");
        } catch (AddressException e) {
            Toast.makeText(HospitalHomeActivity.this,
                    "Email sent error.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (MessagingException e) {
            Toast.makeText(HospitalHomeActivity.this,
                    "Email exception occured.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}