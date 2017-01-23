package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

public interface PermissionServiceAsync {

    void getRunTimePermissionFor(UserInfo u, Shareable sharedObject, AsyncCallback<Permission> callback);

    void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l, AsyncCallback<Void> callback);

    void getAllPermissionsFor(Shareable s, AsyncCallback<Vector<Permission>> callback);

    void shareWith(String userEmail, Shareable sharedObject, Level l, AsyncCallback<Void> callback);

    void getAllPermissionsCreatedBy(UserInfo u, AsyncCallback<Vector<Permission>> callback);

    void deletePermission(Permission p, AsyncCallback<Void> callback);

    void getAllPermissions(AsyncCallback<Vector<Permission>> callback);

}
