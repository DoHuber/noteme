package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

import java.sql.Date;
import java.util.Map;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. Diese Schnittstelle benutzt die gleiche Realisierungsgrundlage wir
 * das Paar {@link BankAdministration} und {@lBankAdministrationImplImpl}. Zu
 * technischen Erläuterung etwa bzgl. GWT RPC bzw. {@link RemoteServiceServlet}
 * siehe {@link BankAdministration} undBankAdministrationImpltungImpl}.
 * </p>
 * <p>
 * Ein ReportGenerator bietet die Möglichkeit, eine Menge von Berichten
 * (Reports) zu erstellen, die Menge von Daten bzgl. bestimmter Sachverhalte des
 * Systems zweckspezifisch darstellen.
 * </p>
 * <p>
 * Die Klasse bietet eine Reihe von <code>create...</code>-Methoden, mit deren
 * Hilfe die Reports erstellt werden können. Jede dieser Methoden besitzt eine
 * dem Anwendungsfall entsprechende Parameterliste. Diese Parameter benötigt der
 * der Generator, um den Report erstellen zu können.
 * </p>
 * <p>
 * Bei neu hinzukommenden Bedarfen an Berichten, kann diese Klasse auf einfache
 * Weise erweitert werden. Hierzu können zusätzliche <code>create...</code>
 * -Methoden implementiert werden. Die bestehenden Methoden bleiben davon
 * unbeeinflusst, so dass bestehende Programmlogik nicht verändert werden muss.
 * </p>
 *
 * @author thies
 */
@RemoteServiceRelativePath("generator")
public interface ReportGenerator extends RemoteService {

    void init() throws IllegalArgumentException;

    AllUserNotebooksR createAllUserNotebooksR() throws IllegalArgumentException;

    AllNotebooksR createAllNotebooksR() throws IllegalArgumentException;

    AllUserNotesR createAllUserNotesR() throws IllegalArgumentException;

    AllNotesR createAllNotesR() throws IllegalArgumentException;

    AllUserPermissionsR createAllUserPermissionsR() throws IllegalArgumentException;

    AllPermissionsR createAllPermissionsR() throws IllegalArgumentException;

    CustomReport createCustomReport(String type, String userEmail, Map<String, Date> timespan, boolean includePermissions);

}
