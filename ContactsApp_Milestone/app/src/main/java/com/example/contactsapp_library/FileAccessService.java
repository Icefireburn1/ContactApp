package com.example.contactsapp_library;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.contactsapp_milestone.MainActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.contactsapp_library.MyApplication;

/**
 * This class accesses a file for the purpose of reading and saving information
 * @author Justin
 *
 */
public class FileAccessService implements IDataAccessService {
	
	private AddressBook addressBook;
	
	/**
	 * Constructor for our service
	 * @param addressBook
	 */
	public FileAccessService(AddressBook addressBook)
	{
		this.addressBook = addressBook;
	}
	
	/**
	 * This function reads our contacts from a JSON file
	 */
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void readAllContacts() {
		try {
			// create object mapper instance
			ObjectMapper mapper = new ObjectMapper();

			addressBook = mapper.readValue(Paths.get(addressBook.getFilePath().toString(),"contacts.json").toFile(), AddressBook.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper function to get past our interface's void return type
	 * @return return a list of contacts
	 */
	@RequiresApi(api = Build.VERSION_CODES.O)
	public List<BaseContact> readContactsFromFileToList()
	{
		readAllContacts();
		
		return this.addressBook.getContacts();
	}

	/**
	 * This function saves our contacts to a JSON file
	 */
	@RequiresApi(api = Build.VERSION_CODES.R)
	@Override
	public void saveAllContacts() {
		saveJSON();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void saveJSON() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			// Convert contacts to JSON file
			mapper.writeValue(Paths.get(addressBook.getFilePath().toString(),"contacts.json").toFile(), addressBook);
		}
		catch (JsonProcessingException  e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
