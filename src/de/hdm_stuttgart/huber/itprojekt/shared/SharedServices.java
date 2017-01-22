package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

/**
 * Created by elcpt on 11.12.2016. Toilettenschüssel
 */
@RemoteServiceRelativePath("login")
public interface SharedServices extends RemoteService {

	public void init() throws IllegalArgumentException;

	public UserInfo login(String requestUri);
	
	public Vector<UserInfo> getAllUsers();

}
