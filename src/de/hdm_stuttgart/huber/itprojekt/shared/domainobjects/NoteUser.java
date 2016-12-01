package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

/**
 * 
 * @author Lisa
 *
 */

public class NoteUser extends DomainObject {
	
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String userName;
	private String surName;
	private String email;
	private String googleId;
	
	public NoteUser(int id, String firstName, String userName, String surName, String email, String googleId) {
		super();

		this.id = id;
		this.firstName = firstName;
		this.userName = userName;
		this.surName = surName;
		this.email = email;
		this.googleId = googleId;
	}

	// DEVELOPMENT!!!
	public NoteUser(int id) {
	    this.id = id;
    }
	
	public NoteUser() {
		
	}

    @Override
    public String toString() {
        return "NoteUser{" +
                "firstName='" + firstName + '\'' +
                ", userName='" + userName + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
	    return this.id;
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
	public void setGoogleId(String googleId) {	//f�llt f�r googleId die NoteUserId weg?
		this.googleId = googleId;
	}

}
