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

	private Date dateOfBirth;
	private String description;
	private String hobby;
	private List<PersonContact> relatives;
	
	public PersonContact(int number, String name, String phone, String hobby, List<Photo> photos, Location location, Date dateOfBirth, String description, PersonContact[] relatives) {
		super(number, name, phone, photos, location);
		
		this.dateOfBirth = dateOfBirth;
		this.description = description;
		this.hobby = hobby;
		this.relatives = new ArrayList<PersonContact>();
	}
	
	public PersonContact()
	{
		super(-1, "Test Name", "3203333333", null, null);
		
		this.dateOfBirth = new Date();
		this.description = "No description set.";
		this.relatives = new ArrayList<PersonContact>();
		this.hobby = "No hobby";
	}
	
	/**
	 * Set all properties at once
	 * @param number
	 * @param name
	 * @param phone
	 * @param hobby
	 * @param photos
	 * @param location
	 * @param dateOfBirth
	 * @param description
	 * @param relatives
	 */
	public void setAll(int number, String name, String phone, String hobby, List<Photo> photos, Location location,
                       Date dateOfBirth, String description, PersonContact[] relatives)
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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
