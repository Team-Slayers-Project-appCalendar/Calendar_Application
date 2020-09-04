package com.example.calendar_application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class PhotoHal extends AppCompatActivity
{
    Context context;
    CalendarView cv;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(context, "jatho mula", Toast.LENGTH_SHORT).show();
    }
}
