package com.example.calendar_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.calendar_application.DBStructure.ID;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_EVENTS_TABLE = "create table "+DBStructure.Event_TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBStructure.EVENT+" TEXT, "+DBStructure.DESCRIPTION+" TEXT, "+DBStructure.LOCATION+" TEXT, "+DBStructure.TIME+" TEXT, "+DBStructure.DATE+" TEXT, "+DBStructure.MONTH+" TEXT, "
            +DBStructure.YEAR+" TEXT)";

    private static final String DROP_EVENTS_TABLE= "DROP TABLE IF EXISTS "+DBStructure.Event_TABLE_NAME;

    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void SaveEvent(String event,String description, String location, String time,String date,String month,String year,SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT,event);
        contentValues.put(DBStructure.DESCRIPTION,description);
        contentValues.put(DBStructure.LOCATION,location);
        contentValues.put(DBStructure.TIME,time);
        contentValues.put(DBStructure.DATE,date);
        contentValues.put(DBStructure.MONTH,month);
        contentValues.put(DBStructure.YEAR,year);
        database.insert(DBStructure.Event_TABLE_NAME, null, contentValues);

    }

    public Cursor getNextId(SQLiteDatabase database){
        String [] Projections = {ID};

        return database.query(DBStructure.Event_TABLE_NAME,Projections,null,null,null,null,ID+" DESC");
    }
    public Cursor ReadEvents(String date, SQLiteDatabase database) {
        String [] Projections = {ID, DBStructure.EVENT, DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.DATE +"=?";
        String [] SelectionArgs = {date};
        return database.query(DBStructure.Event_TABLE_NAME,Projections,Selection,SelectionArgs, null, null, null);
    }

    public Cursor ReadIDEvents(String date,String event, String  time,SQLiteDatabase database){
        String [] Projections = {ID,DBStructure.Notify,DBStructure.TIME};
        String Selection = DBStructure.DATE +"=? and "+DBStructure.EVENT+"=? and "+DBStructure.TIME+"=?";
        String [] SelectionArgs = {date,event,time};

        return database.query(DBStructure.Event_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }

    public Cursor ReadEventsperMonth(String month,String year, SQLiteDatabase database) {
        String [] Projections = {ID,DBStructure.EVENT,DBStructure.DESCRIPTION,DBStructure.LOCATION, DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.MONTH +"=? and "+DBStructure.YEAR+"=?";
        String [] SelectionArgs = {month,year};
        return database.query(DBStructure.Event_TABLE_NAME,Projections,Selection,SelectionArgs, null, null, null);
    }

    public Cursor Getdata (int ID, SQLiteDatabase database) {
        String id=ID+"";
        String [] Projections = {DBStructure.ID,DBStructure.EVENT,DBStructure.DESCRIPTION,DBStructure.LOCATION, DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.ID +"=?";
        String[] SelectionArgs = {id};
        return database.query(DBStructure.Event_TABLE_NAME,Projections,Selection,SelectionArgs, null, null, null);
    }

    public void deleteEvent(String event,String date,String time,SQLiteDatabase database){
        String selection = DBStructure.EVENT+"=? and "+DBStructure.DATE+"=? and "+DBStructure.TIME+"=?";
        String[] selectionArg = {event,date,time};
        database.delete(DBStructure.Event_TABLE_NAME,selection,selectionArg);
    }


    public void updateEvent(String date,String event,String description, String location, String time,String notify,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.Notify,notify);
        String Selection = DBStructure.DATE +"=? and "+DBStructure.EVENT+"=? and "+DBStructure.TIME+"=?";
        String [] SelectionArgs = {date,event,time};
        database.update(DBStructure.Event_TABLE_NAME,contentValues,Selection,SelectionArgs);
    }
}
