package de.hdm_stuttgart.huber.itprojekt.server;

import java.util.Vector;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class PermissionServiceImpl extends RemoteServiceServlet implements PermissionService {
	
	private PermissionMapper permissionMapper = PermissionMapper.getPermissionMapper();
	

	public PermissionServiceImpl() {
		
	}

	public PermissionServiceImpl(Object delegate) {
		super(delegate);
		
	}

	@Override
	public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l) {
		
		Permission p = getRunTimePermissionFor(beneficiary, sharedObject);
		if (p != null) {
			
		}
		
		// Muss eine Permission erstellen die das Objekt für den User beneficiary freigibt
		p = new Permission(l);
		p.setBeneficiary(beneficiary);
		
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			throw new InvalidLoginStatusException("Kein User eingeloggt. Funktion an falscher Stelle verwendet?");
		}
		
		User currentGoogleUser = userService.getCurrentUser();
		UserInfo author = UserInfoMapper.getUserInfoMapper().findUserByGoogleId(currentGoogleUser.getUserId());
		
		p.setAuthor(author);
		
		p.setSharedObject(sharedObject);
		
		permissionMapper.createPermission(p);

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
	public Vector<Permission> getAllPermissionsCreatedBy(UserInfo u) {

		return permissionMapper.getAllPermissionsCreatedBy(u);
		
	}
	
	
	
	

}
