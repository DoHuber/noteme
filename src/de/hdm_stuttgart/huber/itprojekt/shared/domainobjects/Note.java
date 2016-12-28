package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.sql.Date;

public class Note extends DomainObject {
	
	private static final long serialVersionUID = 1L;

	private String content = null;
	private String title = null;
	private String subtitle = null;
	private UserInfo owner = null;
	private NoteBook noteBook = null;
	private Date dueDate = null;
	private Date creationDate = null;
	private Date modificationDate = null;
	
	public Note() {
		
	}
	
	// id wird von DomainObject geerbt
	public Note (int id) {
		this.id = id;
	}


	
	/**
	 * @param noteId
	 * @param content
	 * @param title
	 * @param subtitle
	 * @param owner
	 * @param noteBook
	 * @param dueDate
	 * @param creationDate
	 * @param modificationDate
	 */
	public Note(int noteId, String content, String title, String subtitle, UserInfo owner, NoteBook noteBook,
			Date dueDate, Date creationDate, Date modificationDate) {
		super();
		this.id = noteId;
		this.content = content;
		this.title = title;
		this.subtitle = subtitle;
		this.owner = owner;
		this.noteBook = noteBook;
		this.dueDate = dueDate;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", content=" + content + ", title=" + title + ", subtitle=" + subtitle
				+ ", owner=" + owner + ", noteBook=" + noteBook + ", dueDate=" + dueDate + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + "]";
	}
	
	public String toHtmlString() {
		return "<p> Note <br> [id=" + id + "<br>, content=" + content + "<br>, title=" + title + "<br>, subtitle=" + subtitle
				+ "<br>, owner=" + owner.toString() + "<br>, noteBook=" + noteBook + "<br>, dueDate=" + dueDate + "<br>, creationDate="
				+ creationDate + "<br>, modificationDate=" + modificationDate + "] </p>";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public UserInfo getOwner() {
		return owner;
	}
	public void setOwner(UserInfo owner) {
		this.owner = owner;
	}
	public NoteBook getNoteBook() {
		return noteBook;
	}
	public void setNoteBook(NoteBook noteBook) {
		this.noteBook = noteBook;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
	
	
}