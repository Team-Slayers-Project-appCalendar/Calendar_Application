package com.example.calendar_application;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class IndividualData extends Activity {

DBOpenHelper dbOpenHelper;
Context context;
Events events;
int id = EventRecyclerAdapter.chaiyeko;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_individual);

        final TextView EventName = findViewById(R.id.eventname);
        final TextView EventDescription = findViewById(R.id.eventdescription);
        final TextView EventDate = findViewById(R.id.eventdate);
        final TextView EventTime = findViewById(R.id.eventtime);
        final TextView EventLocation = findViewById(R.id.eventlocation);
        collectData(id);

        EventName.setText(events.getEVENT().toString());
    }
    public void collectData(int ID){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.Getdata(ID,database);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBStructure.ID));
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String description =cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndex(DBStructure.LOCATION));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            events = new Events(id,event,description,location,time,Date,month,Year);
        }
        cursor.close();
        dbOpenHelper.close();
    }
}