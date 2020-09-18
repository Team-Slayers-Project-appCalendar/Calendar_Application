package com.example.calendar_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

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
    }

    @Override
    public void imageClick() {
            Intent open_cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fil = calendarView.gettingImage();
                open_cam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fil));
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

        startActivityForResult(open_cam,1);
            Toast.makeText(this, "parent hit", Toast.LENGTH_SHORT).show();
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

//            int imagename = CalendarView.nextId()+1;
//            File image = new File("mnt/sdcard/myapp/"+imagename+".jpg");
//            if(image.exists())
//                System.out.println();
//                //CalendarView.setImage.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
//            else
//                Toast.makeText(this, "File don't exist", Toast.LENGTH_SHORT).show();
            int value = nextId()+1;
            System.out.println(value);
            Toast.makeText(MainActivity.this, "Ayo ki", Toast.LENGTH_SHORT).show();

           CalendarView.setImage.setImageDrawable(Drawable.createFromPath(fil+""));
            if (requestCode == 1 && resultCode  == RESULT_OK) {
                imageUri = Uri.fromFile(fil);
                try {
                    Toast.makeText(MainActivity.this, "camera ko photo?", Toast.LENGTH_SHORT).show();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
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
