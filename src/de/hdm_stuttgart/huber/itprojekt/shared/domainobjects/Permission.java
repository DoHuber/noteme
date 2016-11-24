package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import com.google.gwt.dev.shell.JavaObject;

public class Permission extends DomainObject {
	
	private static final long serialVersionUID = 1L;
	
	private int permissionId = 0;
	public int level = 0;
	public static final int LEVEL_READ = 0;
	public static final int LEVEL_EDIT = 0;
	public static final int LEVEL_ROOT = 0;
	private JavaObject content = null;
	private NoteUser user = null;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	public static int getLevelRead() {
		return LEVEL_READ;
	}
	public static int getLevelEdit() {
		return LEVEL_EDIT;
	}
	public static int getLevelRoot() {
		return LEVEL_ROOT;
	}
	
	public JavaObject getContent() {
		return content;
	}
	public void setContent(JavaObject content) {
		this.content = content;
	}
	public NoteUser getUser() {
		return user;
	}
	public void setUser(NoteUser user) {
		this.user = user;
	}
	
	

}
