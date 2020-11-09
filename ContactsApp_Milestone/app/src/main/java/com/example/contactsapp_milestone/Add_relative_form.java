package com.example.contactsapp_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contactsapp_library.AddressBook;
import com.example.contactsapp_library.BaseContact;
import com.example.contactsapp_library.BusinessContact;
import com.example.contactsapp_library.ContactAdapter;
import com.example.contactsapp_library.MyApplication;
import com.example.contactsapp_library.PersonContact;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.ArrayList;
import java.util.List;

public class Add_relative_form extends AppCompatActivity {

    ListView lv_myrelatives;
    Button btn_addbyname, btn_relativegoback;
    EditText et_addbyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative_form);

        MyApplication myApp = (MyApplication)this.getApplication();

        AddressBook addressBook = new AddressBook();

        List<BaseContact> bList = new ArrayList<>(myApp.getPersonContact().getRelatives());

        addressBook.setContacts(bList);

        ContactAdapter adapter = new ContactAdapter(Add_relative_form.this, addressBook);

        lv_myrelatives = findViewById(R.id.lv_myrelatives);
        btn_addbyname = findViewById(R.id.btn_addbyname);
        et_addbyname = findViewById(R.id.et_addbyname);
        btn_relativegoback = findViewById(R.id.btn_relativegoback);

        lv_myrelatives.setAdapter(adapter);

        btn_addbyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonContact pc = myApp.getAddressBook().searchForPersonalContactByName(et_addbyname.getText().toString());
                if (pc != null)
                {
                    addressBook.add(pc);
                    myApp.getPersonContact().getRelatives().add(pc);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(myApp, "Sucessfully add contact", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(myApp, "Could not find contact", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_relativegoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewPersonForm.class);
                startActivity(i);
            }
        });
    }
}