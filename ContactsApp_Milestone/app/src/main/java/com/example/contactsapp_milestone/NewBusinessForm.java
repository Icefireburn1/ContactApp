package com.example.contactsapp_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contactsapp_library.BusinessContact;
import com.example.contactsapp_library.Location;
import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.PersonContact;
import com.example.contactsapp_library.Photo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewBusinessForm extends AppCompatActivity {

    Button btn_ok, btn_cancel, btn_editphotos;
    TextView et_name, et_phone, et_businesshours, et_websiteurl, et_state, et_city, et_street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_business_form);

        MyApplication myApp = (MyApplication)this.getApplication();

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_editphotos = findViewById(R.id.btn_editphotos);

        et_websiteurl = findViewById(R.id.et_websiteurl);
        et_businesshours = findViewById(R.id.et_businesshours);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_street = findViewById(R.id.et_street);

        // listen for messages
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT)
        {
            BusinessContact b = myApp.getBusinessContact();

            et_websiteurl.setText(b.getWebsiteURL());
            et_businesshours.setText(b.getBusinessHours());
            et_name.setText(b.getName());
            et_phone.setText(b.getPhone());
            et_state.setText(b.getLocation().getState());
            et_city.setText(b.getLocation().getCity());
            et_street.setText(b.getLocation().getStreet());
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
                if (myApp.getBusinessContact().getPhotos() != null)
                    photos = myApp.getBusinessContact().getPhotos();

                // Location
                String state = et_state.getText().toString();
                String city = et_city.getText().toString();
                String street = et_street.getText().toString();
                Location location = new Location(locationID, state, city, street);

                // Business Contact
                String websiteURL = et_websiteurl.getText().toString();
                String businessHours = et_businesshours.getText().toString();

                BusinessContact bc = new BusinessContact(baseID, name, phone, photos, location, businessHours, websiteURL);

                myApp.getAddressBook().add(bc);
                myApp.setDataRecieved(MyApplication.DATA_SENT.DELETE_BUSINESS_CONTACT);

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
    }
}