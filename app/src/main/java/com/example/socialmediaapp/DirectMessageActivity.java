package com.example.socialmediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DirectMessageActivity extends AppCompatActivity {

    EditText phone;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);
    }

    public void SendMessage(View view) {
        phone = findViewById(R.id.Number_ET);
        message = findViewById(R.id.Message_ET);
        PendingIntent sentPI;
        String SENT = "SMS_SENT";
        String phoneNum = phone.getText().toString();
        String messageStr = message.getText().toString();
        if(!((phoneNum.matches("")) || (messageStr.matches("")))) {
            SmsManager smsManager = SmsManager.getDefault();
            sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
            smsManager.sendTextMessage(phoneNum, null, messageStr, sentPI, null);
            Toast.makeText(getApplicationContext(),"The message has been sent to " + phoneNum + " successfully", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Please fill ALL of the fields before sending!", Toast.LENGTH_SHORT).show();

    }


}