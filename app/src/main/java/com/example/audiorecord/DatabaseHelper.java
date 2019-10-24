package com.example.audiorecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recordsManager";
    public static final String TABLE_NAME = "records";
    public static final String ID = "id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CURRENT_TIME = "currentTime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
                + ID + " INTEGER PRIMARY KEY, "
                + KEY_DATE + " TEXT, "
                + KEY_CURRENT_TIME + " INTEGER" +
                ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecords(Record ro){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_DATE, ro.getDate());
        cv.put(KEY_CURRENT_TIME, ro.getCurrentTime());

        db.insert(TABLE_NAME,null, cv);

    }

    public Record gerRecords(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{ID, KEY_DATE, KEY_CURRENT_TIME}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if(c != null){
            c.moveToFirst();
        }

        Record ro = new Record(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)));
        return ro;
    }

    public List<Record> getAllRecordObject(){
        List<Record> lro = new ArrayList<>();

        String query = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                Record ro = new Record();
                ro.setId(Integer.parseInt(c.getString(0)));
                ro.setDate(c.getString(1));
                ro.setCurrentTime(Integer.parseInt(c.getString(2)));

                lro.add(ro);
            }while (c.moveToNext());
        }

        return  lro;

    }

    public int upgradeRecords(Record ro){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, ro.getDate());
        values.put(KEY_CURRENT_TIME, ro.getCurrentTime());

        return  db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(ro.getId())});
    }

    public void deleteRecord(Record ro){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?" ,new String[]{String.valueOf(ro.getId())});
        db.close();
    }

    public int getCountRecords(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        return c.getCount();
    }
}
