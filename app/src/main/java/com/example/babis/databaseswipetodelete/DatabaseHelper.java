package com.example.babis.databaseswipetodelete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Babis on 2/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {





    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        db =  getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ MyDatabaseContract.DatabaseScheme.TABLE_NAME + " ( " +
                MyDatabaseContract.DatabaseScheme.DATABASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MyDatabaseContract.DatabaseScheme.NAME + " TEXT, " +
                MyDatabaseContract.DatabaseScheme.SURNAME + " TEXT, "+
                MyDatabaseContract.DatabaseScheme.RATING + " TEXT);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop the table if already exists and start a new one
        db.execSQL("DROP TABLE IF EXISTS " + MyDatabaseContract.DatabaseScheme.TABLE_NAME);

        onCreate(db);

    }



    public boolean insertData(String name, String surname, String rating){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabaseContract.DatabaseScheme.NAME,name);
        contentValues.put(MyDatabaseContract.DatabaseScheme.SURNAME,surname);
        contentValues.put(MyDatabaseContract.DatabaseScheme.RATING,rating);
        //if data is not inserted results will be -1
        long result = db.insert(MyDatabaseContract.DatabaseScheme.TABLE_NAME,null,contentValues);

        return result != -1;
    }




    public Cursor getAllData(){
        db = getWritableDatabase();
        return db.rawQuery("select * from "+MyDatabaseContract.DatabaseScheme.TABLE_NAME,null);

    }


    public int deleteData(String name){
       return db.delete(MyDatabaseContract.DatabaseScheme.TABLE_NAME ," name = ?",new String[]{name});
    }


    public boolean deleteDataSwipping(String id){
        return db.delete(MyDatabaseContract.DatabaseScheme.TABLE_NAME, MyDatabaseContract.DatabaseScheme.DATABASE_ID + "=" + id, null) > 0;
    }



    public boolean updateData(String name,String surname, String rating){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabaseContract.DatabaseScheme.NAME,name);
        contentValues.put(MyDatabaseContract.DatabaseScheme.SURNAME,surname);
        contentValues.put(MyDatabaseContract.DatabaseScheme.RATING,rating);
        db.update(MyDatabaseContract.DatabaseScheme.TABLE_NAME,contentValues,"name = ?",new String[]{name});

        return true;
    }

}
