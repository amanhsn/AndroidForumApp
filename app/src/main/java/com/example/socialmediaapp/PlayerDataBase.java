package com.example.socialmediaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PlayerDataBase extends SQLiteOpenHelper {
    private static final String DATABASENAME = "zayan_al_halal.db";
    public static final String TABLE_RECORD = "tblmembers";
    private static final int DATABASEVERSION = 2;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_PHONE = "Phone";

    public static final String TABLE_RECORD2 = "Quotes";
    public static final String COLUMN_Quote = "Quote";
    public static final String COLUMN_Author = "Author";
    public static final String COLUMN_Date = "Date";
    public static final String COLUMN_Tags = "Tags";
    public static final String COLUMN_Title = "Title";

    private SQLiteDatabase database;
    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_PHONE + " TEXT );";

    private static final String CREATE_TABLE_CUSTOMER2 = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD2 + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_Quote + " TEXT," +
            COLUMN_Author + " TEXT," +
            COLUMN_Tags + " TEXT," +
            COLUMN_Date + " TEXT," +
            COLUMN_Title +" TEXT );";


    public PlayerDataBase(Context Context) {super(Context,DATABASENAME, null,DATABASEVERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD2);
        onCreate(sqLiteDatabase);
    }

    public User createUserRecord(User record)
    {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, record.getName());
        values.put(COLUMN_EMAIL, record.getEmail());
        values.put(COLUMN_PASSWORD, record.getPassword());
        values.put(COLUMN_PHONE, record.getPhone());
        long id = database.insert(TABLE_RECORD,null,values);
        System.out.println(id);
        record.setID(id);
        database.close();
        return record;
    }

    public Quote createQuoteRecord(Quote record)
    {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_Quote, record.getQuoteString());
        values.put(COLUMN_Author, record.getAuthorName());
        values.put(COLUMN_Date, record.getDate());
        values.put(COLUMN_Tags, record.getTags());
        values.put(COLUMN_Title, record.getTitle());
        long id = database.insert(TABLE_RECORD2,null,values);
        record.setID(id);
        database.close();
        return record;
    }

    public void deleteMemberByRow(long id)
    {
        database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id,null);
        database.close();
    }

    //class - User
    public static class User {
        private Long ID;
        private String name;
        private String password;
        private String phone;
        private String email;

        public User(String name, String password, String phone, String email) {
            this.name = name;
            this.password = password;
            this.phone = phone;
            this.email = email;
            this.ID = (long)0;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getID() {
            return ID;
        }

        public void setID(Long ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    //class - Quote
    public static class Quote
    {
        private Long ID;
        private String Author_Name;
        private String Quote_Date;
        private String Quote_String;
        private String Quote_Title;
        private String Quote_Tags;

        public Long getID() {
            return ID;
        }

        public void setID(Long ID) {
            this.ID = ID;
        }

        public String getAuthorName()
        {
            return Author_Name;
        }

        public void SetAuthorName(String Name)
        {
            Author_Name = Name;
        }

        public String getQuoteString()
        {
            return Quote_String;
        }

        public void setQuoteString(String Quote)
        {
            Quote_String = Quote;
        }

        public String getDate()
        {
            return Quote_Date;
        }

        public void setDate(String Date)
        {
            Quote_Date = Date;
        }

        public String getTags()
        {
            return Quote_Tags;
        }

        public void setTags(String Date) { Quote_Tags = Date; }

        public String getTitle()
        {
            return Quote_Title;
        }

        public void setTitle(String Date)
        {
            Quote_Title = Date;
        }

        public Quote(String Name, String Quote, String Date, String Title, String Tags)
        {
            this.Author_Name = Name;
            this.Quote_String = Quote;
            this.Quote_Date = Date;
            this.Quote_Title = Title;
            this.Quote_Tags = Tags;
        }
    }
}
