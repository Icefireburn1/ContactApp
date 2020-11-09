package com.example.contactsapp_milestone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Telephony;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactsapp_library.AddressBook;
import com.example.contactsapp_library.BaseContact;
import com.example.contactsapp_library.BusinessContact;
import com.example.contactsapp_library.ContactAdapter;
import com.example.contactsapp_library.Location;
import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.PersonContact;
import com.example.contactsapp_library.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.contactsapp_library.FileAccessService;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv_contacts;
    Button btn_personcontact, btn_businesscontact, btn_search, btn_savefile, btn_loadfile;
    TextView et_search;



    ContactAdapter adapter;

    AddressBook addressBook;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        List<Photo> photoList = new ArrayList<>();
        PersonContact[] personContact = {};
        PersonContact pc = new PersonContact();
        MyApplication myApp = (MyApplication) this.getApplication();
        addressBook = new AddressBook();
        addressBook.setContacts(myApp.getAddressBook().getContacts());

        // listen for messages
        // Delete old contact when editing them from the NewPersonForm
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.DELETE_PERSON_CONTACT)
        {
            addressBook.remove(myApp.getPersonContact());
        }
        if (myApp.getDataRecieved() == MyApplication.DATA_SENT.DELETE_BUSINESS_CONTACT)
        {
            addressBook.remove(myApp.getBusinessContact());
        }

        btn_search = findViewById(R.id.btn_search);
        et_search = findViewById(R.id.et_search);
        lv_contacts = findViewById(R.id.lv_contacts);
        btn_personcontact = findViewById(R.id.btn_personcontact);
        btn_businesscontact = findViewById(R.id.btn_businesscontact);
        btn_savefile = findViewById(R.id.btn_savefile);
        btn_loadfile = findViewById(R.id.btn_loadfile);
        FileAccessService fs = new FileAccessService(addressBook);

        adapter = new ContactAdapter(MainActivity.this, addressBook);
        adapter.notifyDataSetChanged();

        lv_contacts.setAdapter(adapter);

        btn_personcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewPersonForm.class);
                startActivity(i);
            }
        });

        btn_businesscontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewBusinessForm.class);
                startActivity(i);
            }
        });

        // Edit contact
        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), NewPersonForm.class);

                // Handshake between edit form
                if (addressBook.get(position) instanceof PersonContact) {
                    myApp.setPersonContact((PersonContact) addressBook.get(position));
                    myApp.setDataRecieved(MyApplication.DATA_SENT.PERSON_CONTACT);
                    i = new Intent(view.getContext(), NewPersonForm.class);
                }

                // Handshake between edit form
                if (addressBook.get(position) instanceof BusinessContact) {
                    myApp.setBusinessContact((BusinessContact) addressBook.get(position));
                    myApp.setDataRecieved(MyApplication.DATA_SENT.BUSINESS_CONTACT);
                    i = new Intent(view.getContext(), NewBusinessForm.class);
                }

                startActivity(i);
            }
        });



        // Remove contacts
        lv_contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myApp.getAddressBook().remove(position);
                addressBook = myApp.getAddressBook();
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                // TODO: Make more efficient
                String searchText = et_search.getText().toString();
                // Return us to default list if no String entered
                if (searchText.length() == 0) {
                    ContextWrapper cw = new ContextWrapper(v.getContext());
                    File directory = cw.getDir("contacts", Context.MODE_PRIVATE);
                    addressBook.setFilePath(directory.toPath());

                    File files = new File(directory+"/contacts.json");

                    File file = new File(files.getPath());
                    List<BaseContact> importedContacts = fs.readContactsFromFileToList();
                    addressBook.setContacts(importedContacts);
                    myApp.setAddressBook(addressBook);
                    adapter.notifyDataSetChanged();
                    return;
                }

                List<BaseContact> contact = new ArrayList<>();
                if (addressBook.search(et_search.getText().toString()) != null) {
                    contact.add(addressBook.search(et_search.getText().toString()));
                }
                addressBook.setContacts(contact);
                adapter.notifyDataSetChanged();
            }
        });

        btn_savefile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                String filename = "testing.txt";
                String filepath = "";
                String fileContent = "Hello World!";


                ContextWrapper cw = new ContextWrapper(v.getContext());
                File directory = cw.getDir("contacts", Context.MODE_PRIVATE);
                addressBook.setFilePath(directory.toPath());
                fs.saveAllContacts();
            }
        });
        // load file
        btn_loadfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                ContextWrapper cw = new ContextWrapper(v.getContext());
                File directory = cw.getDir("contacts", Context.MODE_PRIVATE);
                addressBook.setFilePath(directory.toPath());

                File files = new File(directory+"/contacts.json");

                File file = new File(files.getPath());
                if (file.exists())
                    Toast.makeText(myApp, "Loaded File", Toast.LENGTH_SHORT).show();
                List<BaseContact> importedContacts = fs.readContactsFromFileToList();
                addressBook.setContacts(importedContacts);
                myApp.setAddressBook(addressBook);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isExternalStorageAvailableForRw() {
        String extStorageState = Environment.getExternalStorageState();
        if (extStorageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}