package com.example.contactsapp_library;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.List;

public class MyApplication extends Application {

    private PersonContact pc = new PersonContact();
    private PersonContact pcToDelete = new PersonContact();
    private BusinessContact bcToDelete = new BusinessContact();
    private BusinessContact bc = new BusinessContact();
    private AddressBook addressBook = new AddressBook();
    private Photo photo = new Photo();
    private DATA_SENT dataRecieved = DATA_SENT.NOTHING;
    private static int idCounter = 0;
    private static Context appContext;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        loadContacts(this, new FileAccessService(this.addressBook));
        appContext = getApplicationContext();
    }

    public enum DATA_SENT {
        NOTHING,
        NEW_PERSON_CONTACT,
        NEW_BUSINESS_CONTACT,
        PERSON_CONTACT,
        BUSINESS_CONTACT,
        DELETE_PERSON_CONTACT,
        DELETE_BUSINESS_CONTACT
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public PersonContact getPersonContact() {
        return pc;
    }

    public void setPersonContact(PersonContact pc) {
        this.pc = pc;
    }

    public BusinessContact getBusinessContact() {
        return bc;
    }

    public void setBusinessContact(BusinessContact bc) {
        this.bc = bc;
    }

    public DATA_SENT getDataRecieved() {
        return dataRecieved;
    }

    public void setDataRecieved(DATA_SENT dataRecieved) {
        this.dataRecieved = dataRecieved;
    }

    public static int getUniqueID() {
        return idCounter++;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public PersonContact getPcToDelete() {
        return pcToDelete;
    }

    public void setPcToDelete(PersonContact pcToDelete) {
        this.pcToDelete = pcToDelete;
    }

    public BusinessContact getBcToDelete() {
        return bcToDelete;
    }

    public void setBcToDelete(BusinessContact bcToDelete) {
        this.bcToDelete = bcToDelete;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadContacts(MyApplication myApp, FileAccessService fs) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("contacts", Context.MODE_PRIVATE);
        addressBook.setFilePath(directory.toPath());

        File files = new File(directory+"/contacts.json");

        File file = new File(files.getPath());
        List<BaseContact> importedContacts = fs.readContactsFromFileToList();
        addressBook.setContacts(importedContacts);

        // We don't want to pass this activites addressbook as a reference so make a new one
        AddressBook tempAddress = new AddressBook();
        tempAddress.setContacts(addressBook.getContacts());
        myApp.setAddressBook(tempAddress);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
