package com.example.contactsapp_milestone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.contactsapp_library.Location;
import com.example.contactsapp_library.MyApplication;
import android.app.Application;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final int PERMISSION_TO_CALL = 1;
    Button btn_ok, btn_cancel, btn_editphotos, btn_editrelatives, btn_call, btn_text, btn_map, btn_email;
    TextView et_date, et_description, et_hobby, et_name, et_phone, et_state, et_city, et_street, et_email;

    MyApplication myApp;

    PersonContact deleteOldContactThatWasEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_form);

        myApp = (MyApplication)this.getApplication();

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_editphotos = findViewById(R.id.btn_editphotos);
        btn_editrelatives = findViewById(R.id.btn_editrelatives);
        btn_call = findViewById(R.id.btn_call);
        btn_text = findViewById(R.id.btn_text);
        btn_map = findViewById(R.id.btn_map);
        btn_email = findViewById(R.id.btn_email);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_description);
        et_hobby = findViewById(R.id.et_hobby);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_street = findViewById(R.id.et_street);
        et_email = findViewById(R.id.et_email);

        // listen for messages
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT ||
        myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_PERSON_CONTACT)
        {
            PersonContact p = myApp.getPersonContact();
            deleteOldContactThatWasEdited = myApp.getPcToDelete();

            if (p.getDateOfBirth() != null)
                et_date.setText(p.getDateOfBirth().toString());
            et_description.setText(p.getDescription());
            et_hobby.setText(p.getHobby());
            et_name.setText(p.getName());
            et_phone.setText(p.getPhone());
            et_state.setText(p.getLocation().getState());
            et_city.setText(p.getLocation().getCity());
            et_street.setText(p.getLocation().getStreet());
            et_email.setText(p.getEmail());
        }

        // Edit photo
        btn_editphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePerson();
                Intent i = new Intent(v.getContext(), EditPhotosForm.class);
                startActivity(i);
            }
        });

        // Save Contact
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonContact pc = savePerson();
                myApp.getAddressBook().add(pc);

                // We are editing a contact so delete the old one
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT) {
                    myApp.setDataRecieved(MyApplication.DATA_SENT.DELETE_PERSON_CONTACT);
                }

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
                savePerson();
                Intent i = new Intent(v.getContext(), Add_relative_form.class);
                startActivity(i);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(et_phone.getText().toString());
            }
        });

        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeMmsMessage(et_phone.getText().toString(), "");
            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(new String[]{et_email.getText().toString()},"Example Subject");
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapsQuery = "geo:0,0?q=" + et_street.getText().toString() + " " + et_city.getText().toString() + " " + et_state.getText().toString();
                Uri mapURI = Uri.parse(mapsQuery);
                showMap(mapURI);
            }
        });
    }

    // Respond to permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_TO_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhoneNumber(et_phone.getText().toString());
                } else {
                    Toast.makeText(this, "Cannot make a call without permission.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_TO_CALL);
        }
        else {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void composeMmsMessage(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // Save our person contact
    private PersonContact savePerson() {
        int baseID = MyApplication.getUniqueID();
        int locationID = MyApplication.getUniqueID();

        // Base Contact
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_email.getText().toString();
        List<Photo> photos = new ArrayList<Photo>();
        if (myApp.getPersonContact().getPhotos() != null)
            photos = myApp.getPersonContact().getPhotos();

        // Location
        String state = et_state.getText().toString();
        String city = et_city.getText().toString();
        String street = et_street.getText().toString();
        Location location = new Location(locationID, street, state, city);

        // Person Contact
        String date = et_date.getText().toString();
        String description = et_description.getText().toString();
        List<PersonContact>relatives = myApp.getPersonContact().getRelatives();
        String hobby = et_hobby.getText().toString();

        PersonContact pc = new PersonContact(baseID, name, phone, hobby, photos, location, email, date, description, relatives);

        myApp.setPersonContact(pc);

        return pc;
    }
}