package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

public class Permission extends DomainObject {

    public static final int LEVEL_READ = 0;
    public static final int LEVEL_EDIT = 0;
    public static final int LEVEL_ROOT = 0;
    private static final long serialVersionUID = 1L;
    public int level = 0;
    private int permissionId = 0;
    private NoteUser user = null;

    public Permission(Object object, int int1, NoteUser noteUser, NoteBook noteBook, Note note) {
        // TODO Auto-generated constructor stub
    }

    public Permission() {

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static int getLevelRead() {
        return LEVEL_READ;
    }

    public static int getLevelEdit() {
        return LEVEL_EDIT;
    }

    public static int getLevelRoot() {
        return LEVEL_ROOT;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public NoteUser getUser() {
        return user;
    }

    public void setUser(NoteUser user) {
        this.user = user;
    }


}
