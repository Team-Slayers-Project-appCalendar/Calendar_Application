package com.example.calendar_application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static android.os.Environment.*;

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

        Bitmap bitmap = null, scaledBitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(eve.get(7))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800,800,false);

        ImageLocation.setImageBitmap(bitmap);
        final Button ShareButton = findViewById(R.id.sharebutton);
        final Bitmap finalBitmap = bitmap;
        final Bitmap finalScaledBitmap = scaledBitmap;
        ShareButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                PdfDocument PdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080,2316 , 1).create();
                PdfDocument.Page page = PdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                String black = "#00000";
                String charcoal = "#313E50";
                String independence = "#3A435E";

                myPaint.setTextSize(50);
                myPaint.setColor(Color.parseColor("#000000"));
                canvas.drawText("Event Details", 380, 80, myPaint);
                canvas.drawLine(0,150,2316,150,myPaint);

                myPaint.setTextSize(50);
                myPaint.setColor(Color.parseColor("#1e89d6"));
                canvas.drawText("Event Name", 30, 250, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#3d6d37"));
                canvas.drawText(eve.get(0), 90, 300, myPaint);

                myPaint.setTextSize(50);
                myPaint.setColor(Color.parseColor("#1e89d6"));
                canvas.drawText("Event Description", 30, 400, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#3d6d37"));
                canvas.drawText(eve.get(1), 90, 450, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#1e89d6"));
                canvas.drawText("Event Date", 30, 550, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#3d6d37"));
                canvas.drawText(eve.get(4), 50, 600, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#1e89d6"));
                canvas.drawText("Event Time:", 540, 550, myPaint);

                myPaint.setTextSize(30);
                myPaint.setColor(Color.parseColor("#3d6d37"));
                canvas.drawText(eve.get(3), 570, 600, myPaint);

                myPaint.setTextSize(50);
                myPaint.setColor(Color.parseColor("#1e89d6"));
                canvas.drawText("Media", 30, 700, myPaint);
                canvas.drawBitmap(finalScaledBitmap, 90, 780, myPaint);


                PdfDocument.finishPage(page);
                File myFile = new File(getExternalStorageDirectory(),"myapp/"+eve.get(0)+".pdf");
                try {
                    PdfDocument.writeTo(new FileOutputStream(myFile));
                    Toast.makeText(IndividualData.this, "File exported successfully. Please check your myapp folder.", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PdfDocument.close();


//                Toast.makeText(IndividualData.this, "Export to pdf starts from here boys", Toast.LENGTH_SHORT).show();
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