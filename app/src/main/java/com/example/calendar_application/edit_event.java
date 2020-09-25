package com.example.calendar_application;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class edit_event extends AppCompatActivity {
    static EditText editeventname,editeventdescription, editeventlocation;
    static TextView displayeventtime, date;
    static ImageButton editeventtime;
    static Button cancel_button, savebutton;
    static ImageView view_image;
    int hours=0,minutes=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_layout);
        editeventname = findViewById(R.id.editeventname);
        editeventdescription = findViewById(R.id.editeventdescription);
        editeventlocation = findViewById(R.id.editeventlocation);
        displayeventtime = findViewById(R.id.displayeventtime);
        editeventtime = findViewById(R.id.editeventtime);
        cancel_button = findViewById(R.id.cancel_button);
        savebutton = findViewById(R.id.savebutton);
        date = findViewById(R.id.date);
        view_image = findViewById(R.id.view_image);
        Intent inte = getIntent();
        ArrayList<String> editdata = inte.getStringArrayListExtra("ehbabukxa");
        editeventname.setText(editdata.get(0));
        editeventdescription.setText(editdata.get(1));
        editeventlocation.setText(editdata.get(2));
        date.setText(editdata.get(4));
        displayeventtime.setText(editdata.get(3));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(editdata.get(7))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        view_image.setImageBitmap(bitmap);

        editeventtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                hours = calendar.get(Calendar.HOUR_OF_DAY);
                minutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(edit_event.this, R.style.Theme_AppCompat_Dialog
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hformat = new SimpleDateFormat( "K:mm a", Locale.ENGLISH);
                        String event_Time = hformat.format(c.getTime());
                        displayeventtime.setText(event_Time);

                    }
                },hours, minutes, false);
                timePickerDialog.show();
            }
        });
    }
}
