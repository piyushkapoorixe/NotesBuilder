package com.piyush.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;

public class NoteActivity extends AppCompatActivity {
    private EditText editText;
    private int id;
    //private ImageView imageView;
    //private ImageView newImageView;
    public Bitmap image;
    //byte[] img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //imageView = findViewById(R.id.image_view);
        //newImageView = findViewById(R.id.image_view_new);

        editText = findViewById(R.id.note_edit_text);
        /* we need to do two things :-
        1) when this activity is created, set the text of this edit text to be the note thats in the database (so when we open this up for the first time, the user sees what was there before)
        2) When user leaves this activity, save the current contents of that EditText back to the database.*/
        // (1)
        String contents = getIntent().getStringExtra("contents");
        final String path = getIntent().getStringExtra("path");
        editText.setText(contents);

        id = getIntent().getIntExtra("id", 0);

        FloatingActionButton delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.database.noteDao().delete(id);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        FloatingActionButton save = findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.database.noteDao().save(editText.getText().toString(), id);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    // (2)
    // onPause is called when we leave the activity
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.database.noteDao().save(editText.getText().toString(), id);
        //Toast.makeText(getApplicationContext(),"Auto saving done..", Toast.LENGTH_SHORT).show();
    }

    /*public void SelectPhoto(View view) {
        // using intent, rather than specifying a class in our own application to open, we can specify a system wide intent so that we can actually open some other app on the user phone
        // Open Document is going to open up the gallery view where we can select a photo
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // specify the type of file we want to open (image/* says we want to open any file whether it be jpeg png gif as long as it is an image)
        intent.setType("image/*");

        // using request code(unique id) we can find from which intent we came back from (in this case qwe are going to gallery and then coming back) since there can be diff intents doing diff such things in our app
        startActivityForResult(intent, 1);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == Activity.RESULT_OK && data!=null) {
            try {
                // URi is  to specify the location(path)  of some data(here the photo that we got)
                Uri uri = data.getData();
                // opening the data // getcontentresolver is a special class that allows to do different file operations
                // rhs returns of type in lhs
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                // changing parcel file descriptor to diff type of object
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                // going from file descriptor to image
                // Factory means its a class whose job is to instantiate objects
                // inside Bitmap Factory class there are some methods to create bitmap objects
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

                // closing this since we dont need it anymore. (opened, decoded, loaded into memory)
                parcelFileDescriptor.close();

                // loading up the image
                //imageView.setImageBitmap(image);
                //Toast.makeText(getApplicationContext(),"Uri:- " + uri.toString(), Toast.LENGTH_SHORT).show();

                //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                //byte[] img = bos.toByteArray();


            } catch (IOException e) {
                Log.e("onactivityresult", "Image not found", e);
            }
        }*/
    }
}
