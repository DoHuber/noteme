package de.hdm_stuttgart.huber.itprojekt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGenerator;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.*;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.DateFilterable.DateType;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

import java.sql.SQLException;
import java.util.*;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface. Die technische
 * Realisierung bzgl. <code>RemoteServiceServlet</code> bzw. GWT RPC erfolgt
 * analog zu {@lBankAdministrationImplImpl}. Für Details zu GWT RPC siehe dort.
 *
 * @author thies
 * @see ReportGenerator
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

    /**
     * Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese
     * die essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
     * bo-Package) bietet.
     */
    private Editor editor = null;

    /**
     * <p>
     * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
     * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
     * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines
     * anderen Konstruktors ist durch die Client-seitige Instantiierung durch
     * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
     * möglich.
     * </p>
     * <p>
     * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
     * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
     * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
     * </p>
     */
    public ReportGeneratorImpl() throws IllegalArgumentException {
    }

    /**
     * Initialsierungsmethode. Siehe dazu Anmerkungen zum
     * No-Argument-Konstruktor.
     *
     * @see #ReportGeneratorImpl()
     */
    @Override
    public void init() throws IllegalArgumentException {
        /*
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine BankVerwaltungImpl-Instanz.
		 */
        EditorImpl a = new EditorImpl();
        a.init();
        this.editor = a;
    }

    /**
     * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
     *
     * @return das BankVerwaltungsobjekt
     */
    protected Editor getNoteBookVerwaltung() {
        return this.editor;
    }

    /**
     * Hinzufügen des Report-Impressums. Diese Methode ist aus den
     * <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
     * diese Tätigkeiten redundant auszuführen hätte. Stattdessen rufen die
     * <code>create...</code>-Methoden diese Methode auf.
     *
     */

    @Override
    public AllUserNotebooksR createAllUserNotebooksR() throws IllegalArgumentException {

        UserInfo u = editor.getCurrentUser();
        return createAllUserNotebooksReportFor(u, new HashMap<String, java.sql.Date>());

    }

    public AllUserNotebooksR createAllUserNotebooksReportFor(UserInfo u, Map<String, java.sql.Date> timespan) throws IllegalArgumentException {

        Vector<Notebook> allNoteBooksForUserId = NoteBookMapper.getNoteBookMapper().getAllNoteBooksForUserId(u.getId());

        if (!timespan.isEmpty()) {
            filterForTimes(allNoteBooksForUserId, timespan);

            if (allNoteBooksForUserId.isEmpty()) {
                return null;
            }
        }

        AllUserNotebooksR report = new AllUserNotebooksR();
        report.setTitle("All notebooks from User");
        report.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Number of notebooks:" +
                allNoteBooksForUserId.size();
        report.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Title")));
        headline.addColumn((new Column("Subtitle")));
        headline.addColumn((new Column("Username")));
        headline.addColumn((new Column("Creation Date")));
        headline.addColumn((new Column("Modification Date")));
        report.addRow(headline);

        for (Notebook element : allNoteBooksForUserId) {

            Row r = new Row();
            r.addColumn(new Column(element.getTitle()));
            r.addColumn(new Column(element.getSubtitle()));
            r.addColumn(new Column(element.getOwner().getNickname()));
            r.addColumn(new Column(element.getCreationDate().toString()));
            r.addColumn(new Column(element.getModificationDate().toString()));

            report.addRow(r);

        }

        report.setImprint(new SimpleParagraph("-----------------------------------------"));

        return report;

    }

    @Override
    public AllNotebooksR createAllNotebooksR() throws IllegalArgumentException {

        Vector<Notebook> allNoteBooks = NoteBookMapper.getNoteBookMapper().getAllNoteBooks();

        AllNotebooksR report2 = new AllNotebooksR();
        report2.setTitle("All notebooks of all User");
        report2.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Number of Notebooks:" +
                allNoteBooks.size();
        report2.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Title")));
        headline.addColumn((new Column("Subtitle")));
        headline.addColumn((new Column("Username")));
        headline.addColumn((new Column("Creation Date")));
        headline.addColumn((new Column("Modification Date")));
        report2.addRow(headline);

        for (Notebook element : allNoteBooks) {

            Row r = new Row();
            r.addColumn(new Column(element.getTitle()));
            r.addColumn(new Column(element.getSubtitle()));
            r.addColumn(new Column(element.getOwner().getNickname()));
            r.addColumn(new Column(element.getCreationDate().toString()));
            r.addColumn(new Column(element.getModificationDate().toString()));

            report2.addRow(r);

        }

        report2.setImprint(new SimpleParagraph("------------------------------------------"));

        return report2;
    }

    @Override
    public AllUserNotesR createAllUserNotesR() throws IllegalArgumentException {

        UserInfo u = editor.getCurrentUser();
        return createAllUserNotesReportFor(u, new HashMap<String, java.sql.Date>());

    }

    public AllUserNotesR createAllUserNotesReportFor(UserInfo u, Map<String, java.sql.Date> timespan)
            throws IllegalArgumentException {

        Vector<Note> allNotesForUserId;
        allNotesForUserId = NoteMapper.getNoteMapper().getAllNotesForUserId(u.getId());

        if (!timespan.isEmpty()) {
            filterForTimes(allNotesForUserId, timespan);

            if (allNotesForUserId.isEmpty()) {
                return null;
            }

        }

        AllUserNotesR report2 = new AllUserNotesR();
        report2.setTitle("All Notes of current User");
        report2.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Number of notes:" +
                allNotesForUserId.size();
        report2.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Title")));
        headline.addColumn((new Column("Subtitle")));
        headline.addColumn((new Column("Username")));
        headline.addColumn((new Column("Creation Date")));
        headline.addColumn((new Column("Modification Date")));
        report2.addRow(headline);

        for (Note element : allNotesForUserId) {

            Row r = new Row();
            r.addColumn(new Column(element.getTitle()));
            r.addColumn(new Column(element.getSubtitle()));
            r.addColumn(new Column(element.getOwner().getNickname()));
            r.addColumn(new Column(element.getCreationDate().toString()));
            r.addColumn(new Column(element.getModificationDate().toString()));

            report2.addRow(r);

        }

        report2.setImprint(new SimpleParagraph("-----------------------------------------"));

        return report2;

    }

    @Override
    public AllNotesR createAllNotesR() throws IllegalArgumentException {
        Vector<Note> allNotes = new Vector<>();
        try {
            allNotes = NoteMapper.getNoteMapper().getAllNotes();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AllNotesR report = new AllNotesR();
        report.setTitle("All Notes of all User");
        report.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Anzahl der Notebooks:" +
                allNotes.size();
        report.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Title")));
        headline.addColumn((new Column("Subtitle")));
        headline.addColumn((new Column("Username")));
        headline.addColumn((new Column("Creation Date")));
        headline.addColumn((new Column("Modification Date")));
        report.addRow(headline);

        for (Note element : allNotes) {

            Row r = new Row();
            r.addColumn(new Column(element.getTitle()));
            r.addColumn(new Column(element.getSubtitle()));
            r.addColumn(new Column(element.getOwner().getNickname()));
            r.addColumn(new Column(element.getCreationDate().toString()));
            r.addColumn(new Column(element.getModificationDate().toString()));

            report.addRow(r);

        }

        report.setImprint(new SimpleParagraph("-------------------------------------------"));

        return report;

    }

    @Override
    public AllUserPermissionsR createAllUserPermissionsR() {

        UserInfo u = editor.getCurrentUser();
        return createAllUserPermissionsReportFor(u);

    }

    public AllUserPermissionsR createAllUserPermissionsReportFor(UserInfo u) {

        Vector<Permission> allPermissionsCreatedBy = new Vector<>();
        allPermissionsCreatedBy.addAll(PermissionMapper.getPermissionMapper().getAllPermissionsCreatedBy(u));

        if (allPermissionsCreatedBy.isEmpty()) {
            return null;
        }

        AllUserPermissionsR report = new AllUserPermissionsR();
        report.setTitle("All Permission from User");
        report.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Number of permissions:" +
                allPermissionsCreatedBy.size();
        report.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Autor")));
        headline.addColumn((new Column("Shared Object")));
        headline.addColumn((new Column("Level")));
        headline.addColumn((new Column("Beneficiary")));
        report.addRow(headline);

        for (Permission element : allPermissionsCreatedBy) {

            Row r = new Row();
            r.addColumn(new Column(element.getAuthor().toString()));
            r.addColumn(new Column(element.getSharedObject().toString()));
            r.addColumn(new Column(Integer.toString(element.getLevelAsInt())));
            r.addColumn(new Column(element.getBeneficiary().toString()));

            report.addRow(r);

        }

        report.setImprint(new SimpleParagraph("----------------------------------------"));

        return report;

    }

    @Override
    public AllPermissionsR createAllPermissionsR() throws IllegalArgumentException {

        Vector<Permission> allPermissions = PermissionMapper.getPermissionMapper().getAllPermissions();

        AllPermissionsR report = new AllPermissionsR();
        report.setTitle("All Permission of all User");
        report.setCreated(new Date(System.currentTimeMillis()));

        String sb = "Number of Permission:" +
                allPermissions.size();
        report.setHeaderData(new SimpleParagraph(sb));

        Row headline = new Row();
        headline.addColumn((new Column("Autor")));
        headline.addColumn((new Column("Shared Object")));
        headline.addColumn((new Column("Level")));
        headline.addColumn((new Column("Beneficiary")));
        report.addRow(headline);

        for (Permission element : allPermissions) {

            Row r = new Row();
            r.addColumn(new Column(element.getAuthor().toString()));
            r.addColumn(new Column(element.getSharedObject().toString()));
            r.addColumn(new Column(Integer.toString(element.getLevelAsInt())));
            r.addColumn(new Column(element.getBeneficiary().toString()));

            report.addRow(r);

        }

        report.setImprint(new SimpleParagraph("---------------------------------------"));

        return report;

    }

    @Override
    public CustomReport createCustomReport(String type, String userEmail, Map<String, java.sql.Date> timespan,
                                           boolean includePermissions) {

        Vector<UserInfo> applicableUsers = new Vector<>();

        if (userEmail.equals("none")) {

            applicableUsers = new SharedServicesImpl().getAllUsers();
            System.out.println("Calling the Mapper for all Users");

        } else {

            applicableUsers.add(UserInfoMapper.getUserInfoMapper().findByEmailAdress(userEmail));
            System.out.println("Calling the Mapper for User with email: " + userEmail);

        }

        System.out.println("User Email: " + userEmail);
        System.out.println("Applicable user count: " + Integer.toString(applicableUsers.size()));
        System.out.println(Arrays.toString(applicableUsers.toArray()));
        System.out.println("Type: " + type);
        System.out.println("Include Perms: " + Boolean.toString(includePermissions));

        CustomReport report = new CustomReport();

        report.setTitle("Custom Report, created on: " + new Date().toString());

        for (UserInfo currentUser : applicableUsers) {

            switch (type) {
                case "notes":

                    if (includePermissions) {

                        appendNotesWithPermissionsTo(report, currentUser, timespan);

                    } else {

                        appendNotesToReport(report, currentUser, timespan);

                    }

                    break;
                case "notebooks":

                    if (includePermissions) {

                        appendNoteBooksWithPermissionsTo(report, currentUser, timespan);

                    } else {

                        appendNoteBooksToReport(report, currentUser, timespan);

                    }

                    break;
                case "permissions":

                    SimpleReport toAdd = createAllUserPermissionsReportFor(currentUser);

                    if (toAdd != null) {
                        toAdd.setHeaderData(new SimpleParagraph("Permissions von: " + currentUser.toString()));
                        report.addSubReport(toAdd);
                    }

                    break;
            }

        }

        report.setHeaderData(new SimpleParagraph(
                "Custom Report on " + type + ", includes Permissions: " + Boolean.toString(includePermissions)));
        report.setImprint(new SimpleParagraph("End of Custom Report"));

        return report;
    }

    @SuppressWarnings("unchecked")
    private <T extends DateFilterable> void filterForTimes(Vector<T> dataSet, Map<String, java.sql.Date> timespan) {

        // Der Synchronisation wegen, sonst hagelt es eine
        // ConcurrentModificationException
        // Erhebend, soweit in Java zu sein
        Vector<DateFilterable> syncedVector = (Vector<DateFilterable>) new Vector<>(dataSet);

        for (DateFilterable element : syncedVector) {

            if (timespan.containsKey("from")) {

                if (element.getDate(DateType.CREATION_DATE).before(timespan.get("from"))) {
                    dataSet.remove(element);
                    continue;
                }

            }

            if (timespan.containsKey("to")) {

                if (element.getDate(DateType.CREATION_DATE).after(timespan.get("to"))) {
                    dataSet.remove(element);
                }

            }

        }

    }

    private void appendNoteBooksToReport(CustomReport report, UserInfo currentUser,
                                         Map<String, java.sql.Date> timespan) {

        SimpleReport toAdd = createAllUserNotebooksReportFor(currentUser, timespan);

        if (toAdd != null) {
            report.addSubReport(toAdd);
        }

    }

    private void appendNoteBooksWithPermissionsTo(CustomReport report, UserInfo currentUser,
                                                  Map<String, java.sql.Date> timespan) {

        Vector<Notebook> allNoteBooks = editor.getAllNoteBooksFor(currentUser);
        PermissionService permService = new PermissionServiceImpl();

        System.out.println("Appending Notebooks w/Permissions Report");
        System.out.println("User has " + Integer.toString(allNoteBooks.size()) + " Notebooks.");
        System.out.println(Arrays.toString(allNoteBooks.toArray()));
        System.out.println("User is: " + currentUser.toString());

        if (!timespan.isEmpty()) {
            filterForTimes(allNoteBooks, timespan);
        }

        for (Notebook element : allNoteBooks) {

            SimpleReport reportOnNotebook = new SimpleReport();
            reportOnNotebook.setTitle("Report on Notebook " + element.getTitle());
            reportOnNotebook.setHeaderData(new SimpleParagraph(element.toString()));

            Vector<Permission> permissionsForNote = permService.getAllPermissionsFor(element);

            Row headerRow = new Row();
            headerRow.addColumn(new Column("id"));
            headerRow.addColumn(new Column("Author"));
            headerRow.addColumn(new Column("Beneficiary"));
            headerRow.addColumn(new Column("Level"));

            reportOnNotebook.addRow(headerRow);

            int permissionCounter = 0;

            for (Permission p : permissionsForNote) {

                Row row = new Row();
                row.addColumn(Integer.toString(p.getId()));
                row.addColumn(p.getAuthor().toString());
                row.addColumn(p.getBeneficiary().toString());
                row.addColumn(Integer.toString(p.getLevelAsInt()));

                reportOnNotebook.addRow(row);

                permissionCounter++;
            }

            reportOnNotebook.setImprint(
                    new SimpleParagraph("Notebook has " + Integer.toString(permissionCounter) + " Permissions"));

            report.addSubReport(reportOnNotebook);

        }

    }

    private void appendNotesToReport(CustomReport report, UserInfo currentUser, Map<String, java.sql.Date> timespan) {

        System.out.println("Appending Notes Report");
        SimpleReport toAdd = createAllUserNotesReportFor(currentUser, timespan);

        if (toAdd != null) {
            report.addSubReport(toAdd);
        }

    }

    private void appendNotesWithPermissionsTo(CustomReport report, UserInfo currentUser,
                                              Map<String, java.sql.Date> timespan) {

        Vector<Note> allNotes = editor.getAllNotesForUser(currentUser);
        PermissionService permService = new PermissionServiceImpl();

        System.out.println("Appending Notes w/Permissions Report");
        System.out.println("User has " + Integer.toString(allNotes.size()) + " Notes.");
        System.out.println(Arrays.toString(allNotes.toArray()));
        System.out.println("User is: " + currentUser.toString());

        if (!timespan.isEmpty()) {
            filterForTimes(allNotes, timespan);
        }

        for (Note element : allNotes) {

            SimpleReport reportOnNote = new SimpleReport();
            reportOnNote.setTitle("Report on Note" + element.getTitle());
            reportOnNote.setHeaderData(new SimpleParagraph(element.toString()));

            Vector<Permission> permissionsForNote = permService.getAllPermissionsFor(element);

            Row headerRow = new Row();
            headerRow.addColumn(new Column("id"));
            headerRow.addColumn(new Column("Author"));
            headerRow.addColumn(new Column("Beneficiary"));
            headerRow.addColumn(new Column("Level"));

            reportOnNote.addRow(headerRow);

            int permissionCounter = 0;

            for (Permission p : permissionsForNote) {

                Row row = new Row();
                row.addColumn(Integer.toString(p.getId()));
                row.addColumn(p.getAuthor().toString());
                row.addColumn(p.getBeneficiary().toString());
                row.addColumn(Integer.toString(p.getLevelAsInt()));

                reportOnNote.addRow(row);

                permissionCounter++;
            }

            reportOnNote.setImprint(
                    new SimpleParagraph("Note has " + Integer.toString(permissionCounter) + " Permissions"));

            report.addSubReport(reportOnNote);

        }

    }

}
