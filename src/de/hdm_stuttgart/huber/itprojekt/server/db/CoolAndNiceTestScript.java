package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import java.util.Vector;

/**
 * Testskript zum Ausprobieren ob die DataMapper richtig funktionieren.
 * Nicht für die produktive Applikation gedacht.
 *
 */
public class CoolAndNiceTestScript {


    public static void main(String[] args) throws Throwable {
    	
    	NoteMapper nm = NoteMapper.getNoteMapper();
    	NoteBookMapper nbm = NoteBookMapper.getNoteBookMapper();
    	PermissionMapper pm = PermissionMapper.getPermissionMapper();
    	
    	Vector<Permission> vnp = pm.getAllPermissionsFor(new NoteBook(6));
    	
    	for (Object o : vnp) {
    	
    		System.out.println(o.toString());
    		
    	}
	
    }



}
