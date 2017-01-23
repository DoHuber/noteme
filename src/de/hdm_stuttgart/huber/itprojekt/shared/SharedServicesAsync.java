package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

public interface SharedServicesAsync {

    void init(AsyncCallback<Void> async);

    void login(String requestUri, AsyncCallback<UserInfo> callback);

    void getAllUsers(AsyncCallback<Vector<UserInfo>> callback);
}
