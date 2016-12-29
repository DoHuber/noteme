package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public interface PermissionServiceAsync {

	void getRunTimePermissionFor(UserInfo u, Shareable sharedObject, AsyncCallback<Permission> callback);

	void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l, AsyncCallback<Void> callback);

	void getAllPermissionsFor(Shareable s, AsyncCallback<Vector<Permission>> callback);

	void shareWith(String userEmail, Shareable sharedObject, Level l, AsyncCallback<Void> callback);

}
