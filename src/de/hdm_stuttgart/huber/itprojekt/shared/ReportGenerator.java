package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserPermissionsR;

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

	public void init() throws IllegalArgumentException;

	public abstract AllUserNotebooksR createAllUserNotebooksR() throws IllegalArgumentException;

	public abstract AllNotebooksR createAllNotebooksR() throws IllegalArgumentException;

	public abstract AllUserNotesR createAllUserNotesR() throws IllegalArgumentException;

	public abstract AllNotesR createAllNotesR() throws IllegalArgumentException;

	public abstract AllUserPermissionsR createAllUserPermissionsR() throws IllegalArgumentException;

	public abstract AllPermissionsR createAllPermissionsR() throws IllegalArgumentException;
}
