package com.example.nitishatal.sensors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;



public class DbHelper2 extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "Gyroscope";    // Database Name

    private static final String TABLE_NAME = "Gyroscope_Table";
    private static final String COL1 = "ID";
    private static final String COL2 = "Timestamp";
    private static final String COL3 = "GX";
    private static final String COL4 = "GY";
    private static final String COL5 = "GZ";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " VARCHAR(255) ,"+ COL3 + " VARCHAR(255) ,"+ COL4 + " VARCHAR(255) ,"+ COL5 + " VARCHAR(225));";


    public DbHelper2(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // String createTable = "CREATE TABLE " + TABLE_NAME + " ("+COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //       COL2 +" VARCHAR(255), "+COL3+" VARCHAR(225);)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addData(String item1, String item2,String item3,String item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, item1);
        cv.put(COL3, item2);
        cv.put(COL4, item3);
        cv.put(COL5, item4);
        //Log.d(TAG, "addData: Adding " + item1 + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, cv);

        //if date as inserted incorrectly it will return -1
        return result;
    }


    public long addAnswer(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL3, item);
        Log.d(TAG, "addAnswer: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, cv);
        return result;

        //if date as inserted incorrectly it will return -1
        /*if (result == -1) {
            return false;
        } else {
            return true;
        }*/
    }

    public String getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT * FROM " + TABLE_NAME;
        //Cursor data = db.rawQuery(query, null);
        //String a=data.getString()
        //return data;
        String[] columns = {COL1, COL2, COL3};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 30; i++) {
            cursor.moveToPosition(i);
            int cid = cursor.getInt(cursor.getColumnIndex(COL1));
            String name = cursor.getString(cursor.getColumnIndex(COL2));
            String password = cursor.getString(cursor.getColumnIndex(COL3));
            buffer.append(cid + "  , " + name + "  , " + password + " \n");
        }
        return buffer.toString();
    }


    public void Update(int id, String newans) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newans + "' WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);

    }


    public void export(FileOutputStream out) {
        SQLiteDatabase db = this.getReadableDatabase(); //My Database class
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        for (int i = 0; i < 100; i++) {
            cursor.moveToPosition(i);
            int index = cursor.getInt(cursor.getColumnIndex(COL1));
            String d1 = cursor.getString(cursor.getColumnIndex(COL2));
            String d2 = cursor.getString(cursor.getColumnIndex(COL3));
            String d3 = cursor.getString(cursor.getColumnIndex(COL4));
            String d4 = cursor.getString(cursor.getColumnIndex(COL5));

//            String d5 = cursor.getString(cursor.getColumnIndex(COL6));
            String data = index + " |" + d1+" |" + d2+ " |"+d3+" |"+d4 + "\n";
            try {
                out.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}

