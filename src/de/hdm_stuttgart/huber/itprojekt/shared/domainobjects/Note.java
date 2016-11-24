package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.util.Date;

public class Note extends DomainObject {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * anlegen von Variablen und den entsprechenden Setter und
	 * Getter Methoden entsprechend dem Klassendiagramm f�r
	 * die Klasse "Note"
	 */
	
	private int noteId = 0;
	private String content = null;
	private String title = null;
	private String subtitle = null;
	private NoteUser owner = null;
	private NoteBook noteBook = null;
	private Date dueDate = null;
	private Date creationDate = null;
	private Date modificationDate = null;
	
	public Note() {
		
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
	public Note(int noteId, String content, String title, String subtitle, NoteUser owner, NoteBook noteBook,
			Date dueDate, Date creationDate, Date modificationDate) {
		super();
		this.noteId = noteId;
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
		return "Note [noteId=" + noteId + ", content=" + content + ", title=" + title + ", subtitle=" + subtitle
				+ ", owner=" + owner + ", noteBook=" + noteBook + ", dueDate=" + dueDate + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + "]";
	}
	
	
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
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
	public NoteUser getOwner() {
		return owner;
	}
	public void setOwner(NoteUser owner) {
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