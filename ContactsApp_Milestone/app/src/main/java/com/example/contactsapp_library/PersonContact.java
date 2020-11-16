package com.example.contactsapp_library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A child of BaseContact that has more personal information about someone
 * @author Justin
 *
 */
public class PersonContact extends BaseContact {

	private String dateOfBirth;
	private String description;
	private String hobby;
	private List<PersonContact> relatives;
	
	public PersonContact(int number, String name, String phone, String hobby, List<Photo> photos, Location location, String email, String dateOfBirth, String description, List<PersonContact> relatives) {
		super(number, name, phone, photos, location, email);
		
		this.dateOfBirth = dateOfBirth;
		this.description = description;
		this.hobby = hobby;
		this.relatives = relatives;
	}
	
	public PersonContact()
	{
		super(-1, "", "", new ArrayList<>(), new Location(), "");
		this.dateOfBirth = "";
		this.description = "";
		this.relatives = new ArrayList<PersonContact>();
		this.hobby = "";
	}
	
	/**
	 * Set all properties at once
	 * @param number id number
	 * @param name of contact
	 * @param phone number for a phone
	 * @param hobby hobby for contact
	 * @param photos list of photos for contact
	 * @param location birth place of contact
	 * @param dateOfBirth time of birth for contact
	 * @param description brief description of contact
	 * @param relatives list of contacts that are related to contact
	 */
	public void setAll(int number, String name, String phone, String hobby, List<Photo> photos, Location location,
                       String dateOfBirth, String description, PersonContact[] relatives)
	{
		super.setNumber(number);
		super.setName(name);
		super.setPhone(phone);
		super.setPhotos(photos);
		super.setLocation(location);		
		this.dateOfBirth = dateOfBirth;
		this.description = description;
		if (relatives != null)
			this.relatives = Arrays.asList(relatives);
		this.hobby = hobby;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PersonContact> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<PersonContact> relatives) {
		this.relatives = relatives;
	}
	
	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
}
