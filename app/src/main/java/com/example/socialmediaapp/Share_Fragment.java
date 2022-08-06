package com.example.socialmediaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.socialmediaapp.MainActivity.CurrentUser;

public class Share_Fragment extends Fragment {

    //important fragment vars
    View v;
    Context Frag_Context;

    //Database vars
    PlayerDataBase db;
    SQLiteDatabase sql;
    Cursor c;

    //Post vars
    EditText Title;
    EditText Post;
    EditText Tags;
    Button InitiatePost;

    public Share_Fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_share_, container, false);
        Frag_Context = this.getContext();
        db = new PlayerDataBase(this.getContext());
        sql = db.getWritableDatabase();
        sql.close();
        AlertDialog.Builder builder = new AlertDialog.Builder(Frag_Context);

        Title = v.findViewById(R.id.Title_ET);
        Post = v.findViewById(R.id.Post_ET);
        Tags = v.findViewById(R.id.Tags_ET);
        InitiatePost = v.findViewById(R.id.InitiatePost);


        InitiatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CurrentUser != null) {
                    builder.setMessage("Are you sure you want to post?").setTitle("Confirm action");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PostContent();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                else
                    Toast.makeText(Frag_Context, "Sign-in in order to post!", Toast.LENGTH_SHORT).show();

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return v;
    }

    public void PostContent() {
            String strTitle = Title.getText().toString();
            String strTags = Tags.getText().toString();
            String strPost = Post.getText().toString();
            PlayerDataBase.Quote newPost = new PlayerDataBase.Quote(CurrentUser.getName(), strPost, GetToday(), strTitle, strTags);
            sql = db.getWritableDatabase();
            db.createQuoteRecord(newPost);
    }

    public static String GetToday(){
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormat.format(presentTime_Date);
    }
}