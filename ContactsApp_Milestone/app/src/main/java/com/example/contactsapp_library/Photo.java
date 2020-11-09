package com.example.contactsapp_library;

import java.util.Date;

/**
 * This class represents the information that would relate to a photo
 * @author Justin
 *
 */
public class Photo {
	private int photoID;
	private String fileName;
	private String description;
	private Date date;

	public Photo()
	{
		this.photoID = -1;
		this.fileName = "No Name";
		this.description = "Description not yet set.";
		this.date = new Date();
	}
	
	public Photo(int photoID, String fileName, String description, Date date)
	{
		this.photoID = photoID;
		this.fileName = fileName;
		this.description = description;
		this.date = date;
	}

	public int getPhotoID() {
		return photoID;
	}

	public void setPhotoID(int photoID) {
		this.photoID = photoID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString()
	{
		return "Photo ID:" + photoID + ". " + fileName + ". " + description + ". " + date;
	}
}
