package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.analyzer.Direct;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    public static PlayerDataBase.User CurrentUser = null;
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(i -> {
            Fragment Current_Fragment = new Home_Fragment();
            switch (i){
                case R.id.Account:
                    Current_Fragment = new Account_Fragment();
                    break;
                case R.id.Share:
                    Current_Fragment = new Share_Fragment();
                    break;
                case R.id.Home:
                    Current_Fragment = new Home_Fragment();
                    break;
                case R.id.Direct:
                    Current_Fragment = new Direct_Fragment();
                    break;
            }
            if(Current_Fragment != null){
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Current_Fragment)
                        .commit();
            }
        });
    }

    public static String buildPrivateNotes(Context context){
        String fileName = (CurrentUser.getName()) + ".txt";
        try{
            InputStream InputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(InputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receivedString;
            String finalStr = "";
            while((receivedString = bufferedReader.readLine()) != null){
                finalStr += receivedString + "\n";
            }
            return finalStr;
        }
        catch (IOException e){System.out.println("bruh something fucked up: " + e);}
        return "";
    }

    public void SwitchToActivityDirect(View view) {
        Intent si = new Intent(this,DirectMessageActivity.class);
        startActivity(si);
    }

    public void SwitchToActivityProjectBook(View view){
        Intent si = new Intent(this, ProjectBook_Activity.class);
        startActivity(si);
    }

    public void SaveContentInPrivateNotes(View view) {
        EditText ET = (findViewById(R.id.PrivateNotes_ET));
        String str = ET.getText().toString();
        String fileName = (CurrentUser.getName()) + ".txt";
        deleteFile(fileName);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));
            outputStreamWriter.write(str);
            outputStreamWriter.close();
            if(!(str.matches("")))
                Toast.makeText(getApplicationContext(), "Note has been saved!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {System.out.println(e.toString());}
    }
}