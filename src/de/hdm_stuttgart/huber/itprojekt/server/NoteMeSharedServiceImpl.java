package de.hdm_stuttgart.huber.itprojekt.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.shared.BullshitException;
import de.hdm_stuttgart.huber.itprojekt.shared.NoteMeSharedService;

/**
 * Created by elcpt on 11.12.2016.
 *
 */
public class NoteMeSharedServiceImpl extends RemoteServiceServlet implements NoteMeSharedService {

    @Override
    public void init() throws IllegalArgumentException {

    }

    @Override
    public String getUserEmail() throws BullshitException {

        return checkUserLoggedIn().getEmail();

    }

    private User checkUserLoggedIn() throws BullshitException {

        UserService userService = UserServiceFactory.getUserService();
        boolean isHeLoggedIn = userService.isUserLoggedIn();
        if (!isHeLoggedIn) {
            throw new BullshitException();
        }
        return userService.getCurrentUser();

    }

}
