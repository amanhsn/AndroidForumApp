package com.example.socialmediaapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Home_Fragment extends Fragment {

    //important fragment vars
    View v;
    Context Frag_Context;

    //Database vars
    PlayerDataBase db;
    SQLiteDatabase sql;
    Cursor c,d;

    //RecycleView shit
    ArrayList<Post> posts = new ArrayList<>();
    int[] pics = {R.drawable.home_bear_1, R.drawable.home_fox_2, R.drawable.home_frog_3, R.drawable.home_giraffe_4,
            R.drawable.home_gorilla_5, R.drawable.home_tiger_6, R.drawable.home_monkey_7, R.drawable.home_snake_8,
            R.drawable.home_lemur_9, R.drawable.home_wolf_10};

    public Home_Fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_, container, false);
        Frag_Context = this.getContext();
        db = new PlayerDataBase(this.getContext());
        sql = db.getWritableDatabase();
        sql.close();
        RecyclerView r = v.findViewById(R.id.mRecycler);
        BuildPostsList();
        Posts_RecycleViewAdapter adapter = new Posts_RecycleViewAdapter(Frag_Context, posts);
        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(Frag_Context));
        return v;
    }

    public void BuildPostsList() {
        sql = db.getReadableDatabase();
        c = sql.query(PlayerDataBase.TABLE_RECORD2,null,null,null,null,null,"ID DESC");
        int usernameCol = c.getColumnIndex(PlayerDataBase.COLUMN_Author);
        int contentCol = c.getColumnIndex(PlayerDataBase.COLUMN_Quote);
        int tagsCol = c.getColumnIndex(PlayerDataBase.COLUMN_Tags);
        int dateCol = c.getColumnIndex(PlayerDataBase.COLUMN_Date);
        int titleCol = c.getColumnIndex(PlayerDataBase.COLUMN_Title);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String name = c.getString(usernameCol);
            int id = abs(name.hashCode());
            posts.add(new Post(pics[id%pics.length], name, c.getString(contentCol), c.getString(titleCol), c.getString(tagsCol), c.getString(dateCol)));
            c.moveToNext();
        }
        c.close();
        sql.close();
    }

}