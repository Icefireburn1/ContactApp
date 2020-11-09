package com.example.contactsapp_library;

/**
 * This class holds the information for a location of a contact (city, state, street)
 * @author Justin
 *
 */
public class Location {

	private int locationID;
	private String street;
	private String state;
	private String city;
	
	public Location(int locationID, String street, String state, String city)
	{
		this.locationID = locationID;
		this.state = state;
		this.city = city;
		this.street = street;
	}
	
	/**
	 * Used by Jackson framework
	 */
	public Location() {
		
	}
	
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString()
	{
		return "Location ID:" + locationID + " " + state + ", " + city + ", " + street;
	}
}
