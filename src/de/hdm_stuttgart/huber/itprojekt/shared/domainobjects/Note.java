package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.sql.Date;

/**
 * Stellt eine Notiz im System dar
 *
 * @author Küchler, Behr
 */
public class Note extends DomainObject implements Shareable, DateFilterable {

    private static final long serialVersionUID = 1L;

    private String content = null;
    private String title = null;
    private String subtitle = null;
    private UserInfo owner = null;
    private Notebook noteBook = null;
    private Date dueDate = null;
    private Date creationDate = null;
    private Date modificationDate = null;
    private String source;

    private Permission runTimePermission;

    public Note() {

    }

    // id wird von DomainObject geerbt
    public Note(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", content=" + content + ", title=" + title + ", subtitle=" + subtitle + ", owner="
                + owner + ", noteBook=" + noteBook + ", dueDate=" + dueDate + ", creationDate=" + creationDate
                + ", modificationDate=" + modificationDate + "]";
    }

    public boolean hasRuntimePermission() {
        return this.runTimePermission != null;
    }

    @Override
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

    public Notebook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(Notebook noteBook) {
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

    @Override
	public Permission getRuntimePermission() {
        return runTimePermission;
    }

    public void setRunTimePermission(Permission runTimePermission) {
        this.runTimePermission = runTimePermission;
    }

    @Override
    public char getType() {
        return 'n';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Date getDate(DateType type) {

        switch (type) {
            case CREATION_DATE:
                return this.creationDate;
            case MODIFICATION_DATE:
                return this.modificationDate;
            case DUE_DATE:
                return this.dueDate;
            default:
                throw new IllegalArgumentException();
        }

    }

}