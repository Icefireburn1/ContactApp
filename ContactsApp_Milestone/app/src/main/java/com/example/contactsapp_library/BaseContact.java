package com.example.contactsapp_library;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This class serves as a parent for other contact classes and contains our fundamental properties of a contact
 * @author Justin
 *
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes(
{
	@Type(value = PersonContact.class, name = "PersonContact"),
	@Type(value = BusinessContact.class, name = "BusinessContact")
})
public abstract class BaseContact {
	

	private int number;
	private String name;
	private String phone;
	private List<Photo> photos;
	private Location location;
	private String email;
	
	
	public BaseContact(int number, String name, String phone, List<Photo> photos, Location location, String email) {
		this.number = number;
		this.name = name;
		this.phone = phone;
		this.photos = photos;
		this.location = location;
		this.email = email;
	}
	
	@Override
	public String toString()
	{
		String photoText = "";
		for (Photo photo : photos) {
			photoText += "\n- " + photo.toString();
		}
		
		return "Contact ID:" + number + " " + name + " " + phone + " " + " " + location.toString() + photoText;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
