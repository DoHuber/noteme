/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @author elcpt
 *
 */
public interface Editor extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public String getHelloWorld();
	
	public NoteBook createNoteBook(NoteBook noteBook);
	
	
	
	
	
	

}
