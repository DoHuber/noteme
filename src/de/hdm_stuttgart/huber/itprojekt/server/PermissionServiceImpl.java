package de.hdm_stuttgart.huber.itprojekt.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
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
		// TODO Auto-generated constructor stub
	}

	public PermissionServiceImpl(Object delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l) {
		
		// Muss eine Permission erstellen die das Objekt für den User beneficiary freigibt
		Permission p = new Permission(l);
		p.setUser(beneficiary);
		p.setSharedObject(sharedObject);
		
		permissionMapper.createPermission(p);

	}

	@Override
	public Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject) {
		
		Permission p = permissionMapper.getPermissionFor(u, sharedObject);
				
		return p;
	}

	@Override
	public Vector<Permission> getAllPermissionsFor(Shareable s) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
