package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

@RemoteServiceRelativePath("PermissionService")
public interface PermissionService extends RemoteService {
	
	public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l);
	
	public void shareWith(String userEmail, Shareable sharedObject, Level l);
	
	public Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject);
	
	public Vector<Permission> getAllPermissionsFor(Shareable s);
	

}
