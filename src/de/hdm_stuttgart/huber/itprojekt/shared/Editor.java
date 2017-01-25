package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;


/**
 * Interface für den Editor-Teil der Applikationslogik, dies hier ist das synchrone Interface, welches von
 * der entsprechenden Implementationsklasse implementiert wird, Letztere bildet das Kernstück der Applikations-
 * logik auf dem Server, neben den entsprechenden Klassen für ReportGenerator, Login und Permissions
 *
 * <p>Anmerkung zum asynchronen Interface: Da das asynchrone Interface halbautomatisch erzeugt wurde und bis auf die
 * Möglichkeit des asynchronen Methodenaufrufes die gleichen Methoden wie in diesem Interface darstellt, dürfte die
 * Javadoc-Dokumentation dieser Methoden die Gegenstücke im asynchronen Interface hinreichend erklären.
 *
 * @author Dominik Huber
 *
 */
@RemoteServiceRelativePath("testEditor")
public interface Editor extends RemoteService {

    // Überschreibt die entsprechende Methode aus der RemoteService-Klasse, siehe JavaDoc derselben.
    void init() throws IllegalArgumentException;

    /**
     * Nur zu Testzwecken für RPC-Calls in der Beta, gibt ohne Parameter
     * einen zufälligen String zurück.
     *
     * @return Zufälliger String, 32 Zeichen lang, alphanumerisch
     */
    String getHelloWorld();

    // CRUD-Methoden für NoteBook

    /**
     * Erstellt ein Notizbuch und gibt das erstellte Notizbuch mit der neu angelegten internen
     * id zurück.
     *
     * @param notebook Das zu erstellende Notizbuch
     * @return Das frisch erstellte Notizbuch
     */
    Notebook createNoteBook(Notebook notebook);

    /**
     * Speichert das übergebene Notizbuch ab und gibt das das aktualisierte Notizbuch zur eventuellen Weiter
     * verwendung zurück. Die id des Notizbuchs ändert sich dabei garantiert nicht.
     *
     * @param noteBook Das zu speichernde Notebook
     * @return Das gespeicherte Notizbuch mit den aktuellen Werten.
     */
    Notebook saveNoteBook(Notebook noteBook);

    /**
     * Findet ein Notizbuch anhand seiner id. Hierzu ist es notwendig,
     * ein <code>Notebook</code>-Objekt zu übergeben, welches nur das <code>id</code>-Feld
     * gesetzt hat. Entsprechend wird ein vollständig <i>befülltes</i> Objekt zurückgegeben
     *
     * @param notebook Das zu holende Notizbuch, mit gesetzter id
     * @return Vollständiges Notizbuch
     * @throws Exception Wirft entweder eine SQLException oder eventuelle Laufzeit-Exceptions weiter
     */
    Notebook getNoteBookById(Notebook notebook) throws Exception;

    /**
     * Löscht das übergebene Notizbuch aus der Datenbank, die Logikschicht vergisst es auch. Der Client hat manuell für
     * Löschung des eventuell verbleibenden Objekts zu sorgen.
     *
     * @param noteBook Das zu löschende Notizbuch
     */
    void deleteNoteBook(Notebook noteBook);

    // CRUD-Methoden für Note

    /**
     * Erstellt eine übergebene Notiz, funktioniert analog zur entsprechenden Methode für
     * <code>Notebook</code>s
     *
     * @param note Die zu erstellende Notiz
     * @return Die erstelle Notiz, mit dem neu vergebenenen id-Attribut
     */
    Note createNote(Note note);

    /**
     * Speichert eine übergebene Notiz ab und gibt die aktualisierte Notiz zurück. Das <code>id</code>-Attribut
     * bleibt dabei garantiert gleich.
     *
     * @param note Die zu speichernde Notiz
     * @return Aktualisierte Notiz
     */
    Note saveNote(Note note);

    /**
     * Findet eine Notiz anhand eines übergebenen <code>Note</code>-Objektes, bei dem lediglich das <code>id</code>
     * -Attribut gesetzt wurde. Zurückgegeben wird das vollständige Objekt
     *
     * @param note Die zu suchende Notiz, mit gesetzter id
     * @return Die vollständiger Notiz
     * @throws Exception Potenziell auftretende Laufzeitexceptions
     */
    Note getNoteById(Note note) throws Exception;

    /**
     * Löscht die übergebene Notiz dauerhaft aus dem System. Aufrufende Schichten haben dabei selbst sorge zu tragen,
     * das lokale Referenzen auf das zu löschende Objekt selbst zerstört werden.
     *
     * @param note Zu löschende Note
     */
    void deleteNote(Note note);

    // CRUD-Methoden nach User, nur nach Login verwendbar!

    /**
     * Gibt alle Notizen des aktuell angemeldeten Users zurück.
     *
     * @return <code>Vector</code> mit allen Notizen des Users
     */
    Vector<Note> getAllNotesForCurrentUser();

    /**
     * Gibt alle Notizen des übergebenen Users zurück
     *
     * @param user User, dessen Notizen geholt werden sollen
     * @return <code>Vector</code> mit den Notizen
     */
    Vector<Note> getAllNotesForUser(UserInfo user);

    /**
     * Holt alle Notizbücher des aktuell angemeldeten Users
     *
     * @return <code>Vector</code> mit allen <code>Notebook</code>s des angemeldeten Nutzers
     */
    Vector<Notebook> getAllNoteBooksForCurrentUser();

    /**
     * Holt alle Notizbücher des übergebenen Users
     *
     * @param u Nutzer, dessen Notizbücher geholt werden sollen
     * @return <code>Vector</code> mit den angeforderten Notizbüchern
     */
    Vector<Notebook> getAllNoteBooksFor(UserInfo u);

    // Methoden mit Freigabe

    /**
     * Holt alle Notizen, welche von anderen Nutzern für den aktuellen Nutzer
     * freigegeben wurden.
     *
     * @return <code>Vector</code> mit allen freigegebenen Notizen
     */
    Vector<Note> getAllSharedNotesForCurrentUser();

    /**
     * Holt alle freigegebenen Notizbücher für den aktuell angemeldeten User
     *
     * @return Alle freigegebenen Notizbücher
     */
    Vector<Notebook> getAllSharedNoteBooksForCurrentUser();

    /**
     * Holt alle Notizen die <i>vom</i> angemeldeten User <i>für</i> andere Nutzer freigegeben
     * wurden.
     *
     * @return Alle vom aktuellen Nutzer für andere freigegebenen Notizen
     */
    Vector<Note> getAllNotesSharedByCurrentUser();

    /**
     * Holt alle Notizbücher die <i>vom</i> angemeldeten User <i>für</i> andere Nutzer freigegeben
     * wurden.
     *
     * @return Alle vom aktuellen Nutzer für andere freigegebenen Notizbücher
     */
    Vector<Notebook> getAllNoteBooksSharedByCurrentUser();

    // Zusätzliche Methoden zu NoteBook

    /**
     * Gibt alle im System verfügbaren Notizbücher zurück. Hat potentielle Kritikalität im Blick auf Datenschutz,
     * diese Methode nur im Administrationskontext verwenden.
     *
     * @return Alle im System befindlichen <code>Notebook</code>s
     */
    Vector<Notebook> getAllNoteBooks();

    /**
     * Gibt alle im übergebenen <code>Notebook</code> enthaltenen <code>Notes</code> zurück.
     *
     * @param nb Notizbuch, dessen zugeordnete Notizen geholt werden sollen.
     * @return Notizen, welche dem übergebenen Notizbuch zugeordnet waren.
     */
    Vector<Note> getAllFrom(Notebook nb);

    /**
     * Gibt alle im System verfügbaren Notizen zurück. Hat potentielle Kritikalität im Blick auf Datenschutz,
     * diese Methode nur im Administrationskontext verwenden.
     *
     * @return Alle im System befindlichen <code>Note</code>s
     */
    Vector<Note> getAllNotes();

    /**
     * Holt die fälligen Notizen des Users und gibt diese zurück. Fälligkeit ist so definiert, dass das
     * Fälligkeitsdatum entweder in der Vergangenheit oder am aktuellen Tag liegen.
     *
     * @return Eventuelle fällige Notizen
     */
    Vector<Note> getDueNotesForCurrentUser();

    /**
     * Ein noch nicht erfasster Nutzer wird in Form des übergebenen UserInfo-Objektes gespeichert.
     *
     * @param user Zu speichernder Nutzer, in Form eines <code>UserInfo</code> Objekts
     * @return fertig gespeichertes <code>UserInfo</code>-Objekt
     */
    UserInfo saveUser(UserInfo user);

    /**
     * Teil der <i>externer Button</i>-Funktionalität. Gibt, wenn vorhanden, einen davor abgefangenen HTTP-referer-
     * Header zurück, ansonsten "none". Hierzu muss sich der anfragende Client in einer vorher serverseitig separat
     * gestarteten HTTP-Session befinden. Siehe hierzu das Nutzerhandbuch,
     *
     * @return <code>String</code> mit dem Inhalt des HTTP referers, oder "none"
     */
    String getSource();

    /**
     * Gibt den aktuell angemeldeten User zurück
     *
     * @return Aktuell angemeldeter Nutzer, als <code>UserInfo</code>-Objekt
     * @throws RuntimeException in jedem Fall, wenn vor dem Login benutzt.
     */
    UserInfo getCurrentUser();

    /**
     * Gibt alle E-Mail-Adressen aller im System gespeicherten Nutzer zurück.
     *
     * @return <code>String-Vector</code>, mit allen E-Mail-Adressen
     */
    Vector<String> getAllEmails();

    /**
     * Löscht einen übergebenen Nutzer, in Form eines <code>UserInfo</code>-Objekts, dauerhaft aus dem System.
     * Dies hat selbstverständlich keinerlei Auswirkungen auf das Google-Konto des Nutzers, jedoch wird er dauerhaft aus
     * dieser Anwendung entfernt.
     *
     * @param ui zu löschender User
     */
    void deleteUserInfo(UserInfo ui);

}
