package com.example.contactsapp_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.Photo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditSinglePhoto extends AppCompatActivity {

    TextView et_filename, et_descr, etd_date;
    Button btn_savephoto, btn_photoback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_photo);
        MyApplication myApp = (MyApplication)this.getApplication();
        Photo photo = myApp.getPhoto();

        et_filename = findViewById(R.id.et_filename);
        et_descr = findViewById(R.id.et_descr);
        etd_date = findViewById(R.id.etd_date);

        btn_savephoto = findViewById(R.id.btn_savephoto);
        btn_photoback = findViewById(R.id.btn_photoback);

        et_filename.setText(photo.getFileName());
        et_descr.setText(photo.getDescription());
        etd_date.setText(photo.getDate().toString());

        btn_savephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditPhotosForm.class);

                // Get date in string format
                @SuppressLint("SimpleDateFormat") DateFormat dFormat = new SimpleDateFormat("MM/dd/yy");
                Date date = new Date();
                try {
                    date = dFormat.parse(etd_date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                photo.setFileName(et_filename.getText().toString());
                photo.setDescription(et_descr.getText().toString());
                photo.setDate(date);


                // listen for specific type of contact
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT)
                {
                    myApp.getBusinessContact().getPhotos().remove(myApp.getPhoto()); // Remove old photo
                    myApp.getBusinessContact().getPhotos().add(photo);
                }
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT)
                {
                    myApp.getPersonContact().getPhotos().remove(myApp.getPhoto()); // Remove old photo
                    myApp.getPersonContact().getPhotos().add(photo);
                }


                startActivity(i);
            }
        });

        btn_photoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditPhotosForm.class);
                startActivity(i);
            }
        });
    }
}