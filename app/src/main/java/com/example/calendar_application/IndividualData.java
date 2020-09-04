package com.example.calendar_application;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IndividualData extends Activity {

String id = EventRecyclerAdapter.chaiyeko;
    ArrayList<String> eve;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_individual);
        final TextView EventName = findViewById(R.id.eventname);
        final TextView EventDescription = findViewById(R.id.eventdescription);
        final TextView EventDate = findViewById(R.id.eventdate);
        final TextView EventTime = findViewById(R.id.eventtime);
        final TextView EventLocation = findViewById(R.id.eventlocation);
        eve=CalendarView.collectData(EventRecyclerAdapter.chaiyeko);
        EventName.setText(eve.get(0));
        EventDescription.setText(eve.get(1));
        EventLocation.setText(eve.get(2));
        EventTime.setText(eve.get(3));
        EventDate.setText(eve.get(4));

//        for (int i = 0; i<eve.size();i++){
//            System.out.println(eve.get(i));
//        }
    }

}