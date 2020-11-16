package com.example.contactsapp_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EditPhotosForm extends AppCompatActivity {

    List<Photo> photos;
    ListView lv_photos;
    FloatingActionButton fab_add;
    Button btn_photolistback;

    // TODO: crashes when there is no contact in the system
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photos_form);

        lv_photos = findViewById(R.id.lv_photos);
        fab_add = findViewById(R.id.fab_add);
        btn_photolistback = findViewById(R.id.btn_photolistback);

        MyApplication myApp = (MyApplication)this.getApplication();
        photos = new ArrayList<>();

        // listen for type of contact
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT ||
        myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_BUSINESS_CONTACT)
        {
            photos = myApp.getBusinessContact().getPhotos();
        }
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT ||
        myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_PERSON_CONTACT)
        {
            photos = myApp.getPersonContact().getPhotos();
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, photos);

        lv_photos.setAdapter(adapter);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.add(new Photo());
                // listen for type of contact
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT ||
                myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_BUSINESS_CONTACT)
                {
                    myApp.getBusinessContact().setPhotos(photos);
                }
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT ||
                myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_PERSON_CONTACT)
                {
                    myApp.getPersonContact().setPhotos(photos);
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Delete item on long press
        lv_photos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // listen for type of contact
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT ||
                myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_BUSINESS_CONTACT)
                {
                    myApp.getBusinessContact().getPhotos().remove(position);
                }
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT ||
                myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_PERSON_CONTACT)
                {
                    myApp.getPersonContact().getPhotos().remove(position);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Open item on tap
        lv_photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), EditSinglePhoto.class);
                myApp.setPhoto(photos.get(position));
                startActivity(i);
            }
        });

        // Go back
        btn_photolistback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // listen for type of contact
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_BUSINESS_CONTACT ||
                        myApp.getDataRecieved() == MyApplication.DATA_SENT.BUSINESS_CONTACT)
                {
                    Intent i = new Intent(v.getContext(), NewBusinessForm.class);
                    startActivity(i);
                }
                if (myApp.getDataRecieved() == MyApplication.DATA_SENT.NEW_PERSON_CONTACT ||
                        myApp.getDataRecieved() == MyApplication.DATA_SENT.PERSON_CONTACT)
                {
                    Intent i = new Intent(v.getContext(), NewPersonForm.class);
                    startActivity(i);
                }
            }
        });
    }
}