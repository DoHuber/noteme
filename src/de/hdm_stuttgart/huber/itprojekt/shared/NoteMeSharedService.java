package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Created by elcpt on 11.12.2016.
 * Toilettenschüssel
 */
public interface NoteMeSharedService extends RemoteService {

    public void init() throws IllegalArgumentException;

    public String getUserEmail() throws BullshitException;

}
