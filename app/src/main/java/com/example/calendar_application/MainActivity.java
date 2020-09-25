package com.example.calendar_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import static com.example.calendar_application.CalendarView.nextId;
import static com.example.calendar_application.CalendarView.setImage;

public class MainActivity extends AppCompatActivity implements CalendarView.ClickImage {

    CalendarView calendarView;
    Uri imageUri, imageUrii;
    static File fil;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        check_permission_camera();


    }
void check_permission_storage(){
    if (ContextCompat.checkSelfPermission(
            MainActivity.this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED) {
        ActivityCompat
                .requestPermissions(
                        MainActivity.this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 10);
    }
    else { }
}
    void check_permission_camera(){
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            MainActivity.this,
                            new String[] { Manifest.permission.CAMERA }, 11);
        }
        else {}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 11) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            check_permission_storage();
        }
        else if (requestCode == 10) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void imageClick() {
            Intent open_cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fil = calendarView.gettingImage();
                open_cam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fil));
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

        startActivityForResult(open_cam,1);
    }

    @Override
    public void gallerySelect() {
        Intent gallery = new Intent();
        gallery.setType("image/+");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 101);
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            int value = nextId()+1;
            System.out.println(value);

           CalendarView.setImage.setImageDrawable(Drawable.createFromPath(fil+""));
            if (requestCode == 1 && resultCode  == RESULT_OK) {
                imageUri = Uri.fromFile(fil);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    CalendarView.setImage.setBackgroundResource(R.color.colorPrimaryDark);
                    CalendarView.setImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    CalendarView.setImage.setImageBitmap(bitmap);
                    e.printStackTrace();
                }
            }

            if (requestCode == 101 && resultCode  == RESULT_OK) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    CalendarView.setImage.setBackgroundResource(R.color.colorPrimaryDark);
                    CalendarView.setImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }
}
