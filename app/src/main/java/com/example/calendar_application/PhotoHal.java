package com.example.calendar_application;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class PhotoHal extends AppCompatActivity
{
private ImageView iv_imageview;
private ImageButton ib_imagebutton;
private Button button;
Uri imageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photohal);
        iv_imageview = findViewById(R.id.iv_imageView);
        ib_imagebutton=findViewById(R.id.ib_imageButton);
        button=findViewById(R.id.addImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check runtime permission
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
//                }



                Intent gallery = new Intent();
                gallery.setType("image/+");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode  == RESULT_OK) {
                imageUri = data.getData();
                Toast.makeText(this, imageUri+"", Toast.LENGTH_SHORT).show();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    iv_imageview.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }
}
