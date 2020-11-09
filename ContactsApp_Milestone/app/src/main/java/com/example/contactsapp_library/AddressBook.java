package com.example.contactsapp_library;

import android.content.Context;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This manages and holds all of our contacts for easy access
 * @author Justin
 *
 */
public class AddressBook {
	private List<BaseContact> contacts;


	public Path getFilePath() {
		return filePath;
	}

	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}

	private Path filePath;
	
	public AddressBook()
	{
		contacts = new ArrayList<BaseContact>();
	}
	
	/**
	 * Add a contact to the list
	 * @param contact
	 */
	public void add(BaseContact contact)
	{
		contacts.add(contact);
	}
	
	/**
	 * Set our contacts to an already existing list
	 * @param contacts A list of contacts
	 */
	public void setContacts(List<BaseContact> contacts)
	{
		this.contacts = contacts;
	}

	/**
	 * Remove a contact from the list
	 * @param contact
	 * @return
	 */
	public boolean remove(BaseContact contact)
	{
		if (contacts.remove(contact))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Remove a contact using the index in the list
	 * @param index
	 * @return
	 */
	public boolean remove(int index)
	{
		if (contacts.remove(index) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Show the information of a contact
	 * @param contact
	 */
	public void display(BaseContact contact)
	{
		System.out.println(contact.toString());
	}
	
	/**
	 * Show all contacts
	 * @param contact
	 */
	public void displayAll()
	{
		for (BaseContact contact : contacts)
		{
			System.out.println(contact.toString());
		}
	}
	
	/**
	 * Returns size of contact list
	 * @return
	 */
	public int size()
	{
		return contacts.size();
	}
	
	/**
	 * Search for a contact by their ID number
	 * @param number
	 * @return BaseContact or null
	 */
	public BaseContact search(int number)
	{
		for (BaseContact baseContact : contacts) {
			if (baseContact.getNumber() == number)
				return baseContact;
		}
		return null;
	}
	/**
	 * Check through any property for a match
	 * @param result
	 * @return baseContact or null
	 */
	public BaseContact search(String result)
	{
		int number;
		try {
			number = Integer.parseInt(result);
		}
		catch(NumberFormatException e)
		{
			number = -1;
		}
		
		// Go through all the contact
		for (BaseContact baseContact : contacts) {
			// Get by name
			if (baseContact.getName().equals(result))
				return baseContact;
			// Get by city
			if (baseContact.getLocation().getCity().equals(result))
				return baseContact;
			// Get ID
			if (baseContact.getNumber() == number)
				return baseContact;
			// Get Phone
			if (baseContact.getPhone().equals(result))
				return baseContact;
			// Get Photo Info
			for (Photo p : baseContact.getPhotos())
			{
				if (p.getPhotoID() == number)
					return baseContact;
				
				if (p.getFileName().equals(result))
					return baseContact;
					
				if (p.getDate().toString().contentEquals(result))
					return baseContact;
				
				if (p.getDescription().equals(result))
					return baseContact;
			}
			
			Location loc = baseContact.getLocation();
			// Get Location Info
			if (loc.getLocationID() == number)
				return baseContact;
			
			if (loc.getStreet().equals(result))
				return baseContact;
			
			if (loc.getCity().equals(result))
				return baseContact;
			
			if (loc.getState().equals(result))
				return baseContact;
			
			// Check for PersonContact specific properties
			if (baseContact instanceof PersonContact)
			{
				PersonContact person = (PersonContact)baseContact;
				if (person.getDateOfBirth().toString().equals(result))
					return baseContact;
				
				if (person.getDescription().equals(result))
					return baseContact;
				
				if (person.getHobby().equals(result))
					return baseContact;
			}
			
			if (baseContact instanceof BusinessContact)
			{
				BusinessContact business = (BusinessContact)baseContact;
				if (business.getBusinessHours().equals(result))
					return baseContact;
				
				if (business.getWebsiteURL().equals(result))
					return baseContact;
			}
			
		}
		return null;
	}
	
	/**
	 * Search for a contact that is a PersonContact
	 * @param name of the person
	 * @return PersonContact
	 */
	public PersonContact searchForPersonalContactByName(String name)
	{
		// Go through all the contact
		for (BaseContact baseContact : contacts) {
			if (baseContact instanceof PersonContact)
			{
				if (baseContact.getName().equals(name))
				{
					return (PersonContact) baseContact;
				}
			}
		}
		
		return null;
	}
	
	public List<BaseContact> getContacts() {
		return contacts;
	}

	/**
	 * Return an item from our list using an index
	 * @param index
	 * @return BaseContact or null
	 */
	public BaseContact get(int index)
	{
		if (index > contacts.size() - 1)
			return null;
		
		return contacts.get(index);
	}
	
	@Override
	public String toString()
	{
		String text = "";
		for (BaseContact baseContact : contacts) {
			text += baseContact.getNumber() + " " + baseContact.getName() + "/n";
		}
		return text;
	}
	
	
}
