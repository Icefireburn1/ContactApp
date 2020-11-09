package com.example.contactsapp_library;

import android.app.Application;

public class MyApplication extends Application {

    public enum DATA_SENT {
        NOTHING,
        PERSON_CONTACT,
        BUSINESS_CONTACT,
        DELETE_PERSON_CONTACT,
        DELETE_BUSINESS_CONTACT
    }

    private PersonContact pc = new PersonContact();
    private BusinessContact bc = new BusinessContact();
    private AddressBook addressBook = new AddressBook();
    private Photo photo = new Photo();
    private DATA_SENT dataRecieved = DATA_SENT.NOTHING;
    private int idCounter = 0;

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

    public int getUniqueID() {
        return idCounter++;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
