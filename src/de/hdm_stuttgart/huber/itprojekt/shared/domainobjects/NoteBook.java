/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.util.Date;

/**
 * @author elcpt
 *
 */
public class NoteBook extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String subtitle;
	private NoteUser owner;
	private Date creationDate;
	private Date modificationDate;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public NoteUser getOwner() {
		return owner;
	}

	public void setOwner(NoteUser owner) {
		this.owner = owner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * 
	 */
	public NoteBook() {
		// TODO Auto-generated constructor stub
	}

}
