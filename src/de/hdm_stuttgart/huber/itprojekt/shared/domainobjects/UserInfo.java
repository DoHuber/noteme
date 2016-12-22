package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

/**
 * 
 * @author Lisa
 *
 */

public class UserInfo extends DomainObject {
	
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String surName;
	private String googleId;
	
	// AppEngine Users API
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	
	private boolean isAdmin;
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdminStatus(boolean status) {
		this.isAdmin = status;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public UserInfo(int id, String firstName, String userName, String surName, String email, String googleId) {
		super();

		this.id = id;
		this.firstName = firstName;
		this.nickname = userName;
		this.surName = surName;
		this.emailAddress = email;
		this.googleId = googleId;
	}

	// DEVELOPMENT!!!
	public UserInfo(int id) {
	    this.id = id;
    }
	
	public UserInfo() {
		
	}
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	@Override
	public String toString() {
		return "UserInfo [firstName=" + firstName + ", surName=" + surName + ", googleId=" + googleId + ", loggedIn="
				+ loggedIn + ", loginUrl=" + loginUrl + ", logoutUrl=" + logoutUrl + ", emailAddress=" + emailAddress
				+ ", nickname=" + nickname + "]";
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String userName) {
		this.nickname = userName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {	//f�llt f�r googleId die NoteUserId weg?
		this.googleId = googleId;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	

}
