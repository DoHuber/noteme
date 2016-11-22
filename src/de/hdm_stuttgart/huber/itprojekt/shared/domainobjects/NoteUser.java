package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

/**
 * 
 * @author Lisa
 *
 */

public class NoteUser extends DomainObject {
	
	private static final long serialVersionUID = 1L;
	
	private int noteUserId = 0;
	private String firstName = null;
	private String userName = null;
	private String surName = null;
	private String email = null;
	private String googleId = null;
	
	public int getNoteUserId() {
		return noteUserId;
	}
	public void setNoteUserId(int noteUserId) {
		this.noteUserId = noteUserId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {	//fällt für googleId die NoteUserId weg?
		this.googleId = googleId;
	}

}
