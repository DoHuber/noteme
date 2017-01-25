package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * Auslagerung aller Methoden, die rund um das Freigabesystem stattfinden,
 * mit Ausnahme derer, die andere <code>DomainObjects</code> zurückgeben, d.h. <code>Note und Notebook</code>.
 *
 * <p>Die Methoden dieses Interfaces werden benutzt, um beliebige Objekte, welche das Shareable-Interface
 * implementieren, mit anderen Nutzern zu teilen. Dabei werden laufend entsprechende <code>Permission</code>-Objekte
 * erzeugt.
 *
 * <p>Diese Dokumentation soll auch als Dokumentation für das entsprechende asynchrone, halbautomatisch erzeugte,
 * Interface dienen.
 *
 * @see Shareable
 * @see Level
 *
 */
@RemoteServiceRelativePath("PermissionService")
public interface PermissionService extends RemoteService {

    /**
     * Teilt das übergebene teilbare (d.h. <code>Shareable</code> implementierende) Objekt mit dem
     * übergebenen Nutzer, dabei wird das übergebene <code>Level</code> eingestellt.
     *
     * @param beneficiary Nutzer, mit dem geteilt werden soll
     * @param sharedObject Das zu teilende Objekt
     * @param l <code>Level</code>, oder Stufe, des Zugriffs, die der Nutzer erhalten soll.
     */
    void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l);

    /**
     * Überladung der obenstehenden Methode, nur dass hier die E-Mail-Adresse des Zielnutzers übergeben wird.
     *
     * @param userEmail E-Mail-Adresse des Nutzers, mit dem geteilt werden soll
     * @param sharedObject Zu teilendes Objekt
     * @param l <code>Level</code>, oder Stufe, des Zugriffs, die der Nutzer erhalten soll.
     */
    void shareWith(String userEmail, Shareable sharedObject, Level l);

    /**
     * Zentraler Aspekt der Freigabefunktionalität: Holt die <code>Permission</code>, die ggf. für den
     * übergebenen Nutzer und das übergebene teilbare Objekt zutrifft. Damit kann zur Laufzeit schnell herausgefunden
     * werden, welchen Zugriff der Nutzer auf das übergebene Objekt hat.
     *
     * @param u Nutzer, der aktuell angemeldet ist.
     * @param sharedObject Zu prüfendes teilbares Objekt
     * @return <code>Permission</code>, welche Auskunft über die Berechtigungen des Nutzers gibt
     */
    Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject);

    /**
     * Gibt alle für ein teilbares Objekt zutreffende Freigaben zurück
     *
     * @param s Teilbares Objekt, für das <code>Permissions</code> geholt werden sollen
     * @return Alle anwendbaren Permissions
     */
    Vector<Permission> getAllPermissionsFor(Shareable s);

    /**
     * Gibt alle Freigaben, die vom übergebenen Nutzer für andere Nutzer
     * erstellt wurden.
     *
     * @param u Nutzer, dessen Freigaben geholt werden sollen
     * @return Alle anwendbaren Freigaben
     */
    Vector<Permission> getAllPermissionsCreatedBy(UserInfo u);

    /**
     * Löscht die übergebene Freigabe. Freigegebene Objekte bleiben davon unberührt.
     *
     * @param p Zu löschende <code>Permission</code>
     */
    void deletePermission(Permission p);

    /**
     * Gibt alle im System verfügbaren Freigaben zurück
     *
     * @return alle <code>Permissions</code>
     */
    Vector<Permission> getAllPermissions();

}
