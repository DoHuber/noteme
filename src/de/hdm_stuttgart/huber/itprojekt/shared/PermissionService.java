package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

@RemoteServiceRelativePath("PermissionService")
public interface PermissionService extends RemoteService {

    void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l);

    void shareWith(String userEmail, Shareable sharedObject, Level l);

    Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject);

    Vector<Permission> getAllPermissionsFor(Shareable s);

    Vector<Permission> getAllPermissionsCreatedBy(UserInfo u);

    void deletePermission(Permission p);

    Vector<Permission> getAllPermissions();

}
