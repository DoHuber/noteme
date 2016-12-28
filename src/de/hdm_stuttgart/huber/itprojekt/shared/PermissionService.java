package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public interface PermissionService extends RemoteService {
	
	public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l);
	
	public Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject);
	

}
