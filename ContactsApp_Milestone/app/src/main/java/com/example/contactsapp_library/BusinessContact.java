package com.example.contactsapp_library;

import java.util.List;

/**
 * A child of the BaseContact class that relates to the business side of contacts
 * @author Justin
 *
 */
public class BusinessContact extends BaseContact {

	private String businessHours;
	private String websiteURL;
	
	public BusinessContact(int number, String name, String phone, List<Photo> photos, Location location, String businessHours, String websiteURL) {
		super(number, name, phone, photos, location);
		
		this.businessHours = businessHours;
		this.websiteURL = websiteURL;
	}

	public BusinessContact() {
		super(-1,"No Name", "000-000-0000",null,null);
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	
	

}
