package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.DateFilterable;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

import java.sql.Date;
import java.util.Map;

/**
 * Übernommen aus dem Bankprojekt von Prof. Dr. Thies und Prof. Dr. Rathke,
 * zur Verfügung gestellt auf dem Moodle-Kurs zum IT-Projekt. An das Notizbuch-Szenario angepasste
 * Methoden basieren dabei maßgeblich auf dieser Vorlage.
 *
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. Diese Schnittstelle benutzt die gleiche Realisierungsgrundlage wir
 * das Paar [..]
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
 * <p>Analog zu den anderen Interfaces dokumentiert auch hier dieses Interface das asynchrone gleich mit,
 * da diese sich nur minimal unterscheiden.</p>
 *
 * @author thies
 */
@RemoteServiceRelativePath("generator")
public interface ReportGenerator extends RemoteService {

    void init() throws IllegalArgumentException;

    /**
     * Gibt einen Report zurück, der alle Notizbücher aller Nutzer umfasst, d.h.
     * aller Notizbücher mit Informationen zu ihren Autoren
     *
     * @return Report über alle Notizbücher und Nutzer
     * @throws IllegalArgumentException im Fehlerfall
     */
    AllUserNotebooksR createAllUserNotebooksR() throws IllegalArgumentException;

    /**
     * Gibt einen Report über alle Notizbücher zurück, ohne Informationen über
     * ihre Autoren
     *
     * @return Report über alle Notizbücher
     * @throws IllegalArgumentException im Fehlerfall
     */
    AllNotebooksR createAllNotebooksR() throws IllegalArgumentException;

    /**
     * Gibt einen Report zurück, der alle Notizen aller Nutzer umfasst, d.h.
     * aller Notizbücher mit Informationen zu ihren Autoren
     *
     * @return Report über alle Notizen und Nutzer
     * @throws IllegalArgumentException im Fehlerfall
     */
    AllUserNotesR createAllUserNotesR() throws IllegalArgumentException;

    /**
     * Gibt einen Report über alle Notizen zurück, ohne Informationen über
     * ihre Autoren
     *
     * @return Report über alle Notizen
     * @throws IllegalArgumentException im unerwarteten Fehlerfall
     */
    AllNotesR createAllNotesR() throws IllegalArgumentException;

    /**
     * Gibt einen Bericht über alle Nutzer und Freigaben zurück
     *
     * @return Report mit allen Nutzern und Freigaben
     * @throws IllegalArgumentException im unerwarteten Fehlerfall, intern
     */
    AllUserPermissionsR createAllUserPermissionsR() throws IllegalArgumentException;

    /**
     * Gibt einen Report über alle Freigaben zurück, ohne Informationen über
     * ihre Ersteller
     *
     * @return Freigabenreport
     * @throws IllegalArgumentException im Fehlerfall, unerwartet
     */
    AllPermissionsR createAllPermissionsR() throws IllegalArgumentException;

    /**
     * Gibt einen benutzerdefinierten <code>CustomReport</code> zurück, welcher mit den
     * übergebenen Parametern stark angepassst werden kann.
     *
     * @param type <code>String</code>, welcher den Typ des Reports angibt (notes, notebooks, permissions)
     * @param userEmail Email des Users, zu dem der Report erstellt werden soll, kann <code>null</code> sein
     * @param timespan Zeitspanne in Form einer <code>Map</code> mit Datumsangaben, kann <code>null</code> oder leer sein
     * @param includePermissions Gibt an, ob in anwendbaren Reports Freigaben erwähnt werden sollen
     * @param dateType Gibt an, welche Datumswerte, wenn anwendbar, bei der Erstellung verwendet werden sollen
     * @return Den fertigen benutzerdefinierten <code>CustomReport</code>
     *
     * @see CustomReport
     */
    CustomReport createCustomReport(String type, String userEmail, Map<String, Date> timespan, boolean includePermissions, DateFilterable.DateType dateType);

}
