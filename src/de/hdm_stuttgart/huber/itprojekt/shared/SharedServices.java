package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * Wird zur Anmeldung mit dem Google AccountEditView über die Google App Engine Users - API verwendet
 * Dokumentiert analog auch das ähnlich benannte asynchrone Interface mit.
 *
 * @author Dominik Huber
 */
@RemoteServiceRelativePath("login")
public interface SharedServices extends RemoteService {

    /**
     * Initialisierung des Servlets, geerbt von Superklasse
     *
     * @throws IllegalArgumentException im Fehlerfall
     * @see RemoteService
     */
    void init() throws IllegalArgumentException;

    /**
     * Stellt fest, ob ein Nutzer angemeldet ist, wenn nein, wird ein Loginlink übergeben,
     * ansonsten das volle <code>UserInfo</code>-Objekt
     *
     * @param requestUri URI zum Login
     * @return UserInfo-Objekt des Users oder mit Login-URL
     */
    UserInfo login(String requestUri);

    /**
     * Gibt alle im System verfügbaren Nutzer zurück
     * @return Alle erhältlichen Nutzer
     */
    Vector<UserInfo> getAllUsers();

}
