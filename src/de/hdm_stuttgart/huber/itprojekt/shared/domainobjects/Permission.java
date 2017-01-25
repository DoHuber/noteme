package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

/**
 * Stellt Freigaben von teilbaren Objekten dar
 *
 * @author Küchler, Behr
 */
public class Permission extends DomainObject {

    // Hat eclipse mal automatisch generiert
    private static final long serialVersionUID = 1L;

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

    public boolean isUserAllowedTo(Level action) {

        return this.level.value >= action.value;

    }

    @Override
    public String toString() {
        return "Permission [level=" + level + ", author=" + author + ", beneficiary=" + beneficiary + ", sharedObject="
                + sharedObject + ", id=" + id + "]";
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

    public int getId() {
        return id;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    // Enumerator, hier besser geeignet
    public enum Level {

        NONE(0), READ(10), EDIT(20), DELETE(30);

        private int value;

        Level(int i) {
            value = i;
        }

    }

}
