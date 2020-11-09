package com.example.contactsapp_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.contactsapp_library.Location;
import com.example.contactsapp_library.MyApplication;
import android.app.Application;
import android.app.Activity;
import android.widget.TextView;

import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.PersonContact;
import com.example.contactsapp_library.Photo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewPersonForm extends AppCompatActivity {

    Button btn_ok, btn_cancel, btn_editphotos, btn_editrelatives;
    TextView et_date, et_description, et_hobby, et_name, et_phone, et_state, et_city, et_street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_form);

        MyApplication myApp = (MyApplication)this.getApplication();

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_editphotos = findViewById(R.id.btn_editphotos);
        btn_editrelatives = findViewById(R.id.btn_editrelatives);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_description);
        et_hobby = findViewById(R.id.et_hobby);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_street = findViewById(R.id.et_street);

        // listen for messages
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT)
        {
            PersonContact p = myApp.getPersonContact();

            et_date.setText(p.getDateOfBirth().toString());
            et_description.setText(p.getDescription());
            et_hobby.setText(p.getHobby());
            et_name.setText(p.getName());
            et_phone.setText(p.getPhone());
            et_state.setText(p.getLocation().getState());
            et_city.setText(p.getLocation().getCity());
            et_street.setText(p.getLocation().getStreet());
        }

        btn_editphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditPhotosForm.class);
                startActivity(i);
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int baseID = myApp.getUniqueID();
                int locationID = myApp.getUniqueID();

                // Base Contact
                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                List<Photo> photos = new ArrayList<Photo>();
                if (myApp.getPersonContact().getPhotos() != null)
                    photos = myApp.getPersonContact().getPhotos();

                // Location
                String state = et_state.getText().toString();
                String city = et_city.getText().toString();
                String street = et_street.getText().toString();
                Location location = new Location(locationID, state, city, street);

                // Person Contact
                @SuppressLint("SimpleDateFormat") DateFormat dFormat = new SimpleDateFormat("MM/dd/yy");
                Date date = new Date();
                try {
                    date = dFormat.parse(et_date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String description = et_description.getText().toString();
                PersonContact[] pcs = new PersonContact[0];
                String hobby = et_hobby.getText().toString();


                PersonContact pc = new PersonContact(baseID, name, phone, hobby, photos, location, date, description, pcs);

                myApp.getAddressBook().add(pc);
                myApp.setDataRecieved(MyApplication.DATA_SENT.DELETE_PERSON_CONTACT);

                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btn_editrelatives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Add_relative_form.class);
                startActivity(i);
            }
        });
    }
}