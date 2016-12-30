package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

public class Permission extends DomainObject {
	
	// Hat eclipse mal automatisch generiert
	private static final long serialVersionUID = 1L;
    
    // Enumerator, hier besser geeignet
    public enum Level {
    	
    	NONE(0), READ(10), EDIT(20), DELETE(30);
    	
    	private int value;
    	private Level(int i) {
    		value = i;
    	}
    	
    		
    }
   
    private Level level;
    private UserInfo author;
    private UserInfo beneficiary;
    private Shareable sharedObject;
    
    

    // Konstruktoren
    public Permission(int id, Level l) {
    	this.id = id;
    	this.level = l;
    }
    
    public Permission(Level l) {
    	this.level = l;
    }
    
    public Permission() {
    	this.level = Level.NONE;
    }
    
       
    @Override
	public String toString() {
		return "Permission [level=" + level + ", user=" + beneficiary + ", sharedObject=" + sharedObject + ", id=" + id + "]";
	}

	public boolean isUserAllowedTo(Level action) {
    	
    	if (this.level.value >= action.value) {
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
    
    // Getter und Setter
	public UserInfo getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(UserInfo user) {
		this.beneficiary = user;
	}

	public Shareable getSharedObject() {
		return sharedObject;
	}

	public void setSharedObject(Shareable sharedObject) {
		this.sharedObject = sharedObject;
	}
	
	public int getLevelAsInt() {
		return this.level.value;
	}

	public UserInfo getAuthor() {
		return author;
	}

	public void setAuthor(UserInfo author) {
		this.author = author;
	}
	
	
	

}
