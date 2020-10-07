package com.example.calendar_application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IndividualData extends AppCompatActivity {

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
        final ImageView ImageLocation = findViewById(R.id.view_image);
        final TextView eventid = findViewById(R.id.eventttid);
        final Button editbutton = findViewById(R.id.editbutton);

        eve=CalendarView.collectData(EventRecyclerAdapter.chaiyeko);
        EventName.setText(eve.get(0));
        EventDescription.setText(eve.get(1));
        EventLocation.setText(eve.get(2));
        EventTime.setText(eve.get(3));
        EventDate.setText(eve.get(4));
        eventid.setText(EventRecyclerAdapter.chaiyeko);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(eve.get(7))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageLocation.setImageBitmap(bitmap);
        final Button ShareButton = findViewById(R.id.sharebutton);
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(IndividualData.this, "Export to pdf starts from here boys", Toast.LENGTH_SHORT).show();
//                Toast.makeText(IndividualData.this, ""+eve.get(0), Toast.LENGTH_SHORT).show();
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualData.this,edit_event.class);
                intent.putStringArrayListExtra("ehbabukxa",eve);
                startActivity(intent);
            }
        });
    }

}