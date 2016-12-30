package de.hdm_stuttgart.huber.itprojekt.server;

import java.util.Vector;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class PermissionServiceImpl extends RemoteServiceServlet implements PermissionService {
	
	/**
	 *  Das war eclipse
	 */
	private static final long serialVersionUID = -1339547511763075795L;
	
	private PermissionMapper permissionMapper = PermissionMapper.getPermissionMapper();
	

	public PermissionServiceImpl() {

	}

	public PermissionServiceImpl(Object delegate) {
		super(delegate);

	}

	@Override
	public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l) {
		
		// Muss eine Permission erstellen die das Objekt für den User beneficiary freigibt
		/**
		 * Gedanken zur Logik:
		 * Wenn eine Permission existiert, die gesteigert werden soll, ist sie zu finden.
		 * In diesem Fall dann die Permission verändern und abspeichern, mit höherem Rang.
		 *
		 * Wenn noch keine Permission existiert, neue anlegen.
		 */
		Permission p = permissionMapper.getPermissionFor(beneficiary, sharedObject);
		if (p == null) {
			
			p = new Permission();
			p.setLevel(l);
			setCurrentUserAsAuthor(p);
			createNewPermission(p, beneficiary, sharedObject);
			
		} else {
			
			upgradeExistingPermissionTo(p, l);
			
		}


	}
	
	private void setCurrentUserAsAuthor(Permission p) {
		
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			throw new InvalidLoginStatusException("Kein User eingeloggt. Funktion an falscher Stelle verwendet?");
		}
		
		User currentGoogleUser = userService.getCurrentUser();
		UserInfo author = UserInfoMapper.getUserInfoMapper().findUserByGoogleId(currentGoogleUser.getUserId());
	
		p.setAuthor(author);
		
	}

	private void createNewPermission(Permission p, UserInfo beneficiary, Shareable sharedObject) {

		p.setBeneficiary(beneficiary);
		p.setSharedObject(sharedObject);
		permissionMapper.createPermission(p);

	}

	private void upgradeExistingPermissionTo(Permission p, Level l) {

		p.setLevel(l);
		permissionMapper.savePermission(p);

	}

	@Override
	public void shareWith(String userEmail, Shareable sharedObject, Level l) {

		UserInfo userToShareWith = UserInfoMapper.getUserInfoMapper().findByEmailAdress(userEmail);
		shareWith(userToShareWith, sharedObject, l);

	}

	@Override
	public Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject) {
		
		Permission p = permissionMapper.getPermissionFor(u, sharedObject);
				
		return p;
	}

	@Override
	public Vector<Permission> getAllPermissionsFor(Shareable s) {
		
		return permissionMapper.getAllPermissionsFor(s);
		
	}

	@Override
	public void deletePermission(Permission p) {

		permissionMapper.deletePermission(p);

	}

	@Override
	public Vector<Permission> getAllPermissionsCreatedBy(UserInfo u) {

		return permissionMapper.getAllPermissionsCreatedBy(u);

	}

	
	
	

}
