package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * Created by elcpt on 11.12.2016. Toilettenschüssel
 */
@RemoteServiceRelativePath("login")
public interface SharedServices extends RemoteService {

    void init() throws IllegalArgumentException;

    UserInfo login(String requestUri);

    Vector<UserInfo> getAllUsers();

}
