package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface NoteMeSharedServiceAsync {

    void init(AsyncCallback<Void> async);

    void getUserEmail(AsyncCallback<String> async);
}
