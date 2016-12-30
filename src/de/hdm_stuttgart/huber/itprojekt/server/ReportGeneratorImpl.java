package de.hdm_stuttgart.huber.itprojekt.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.BullshitException;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGenerator;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.Column;
import de.hdm_stuttgart.huber.itprojekt.shared.report.CompositeParagraph;
import de.hdm_stuttgart.huber.itprojekt.shared.report.CompositeReport;
import de.hdm_stuttgart.huber.itprojekt.shared.report.Row;
import de.hdm_stuttgart.huber.itprojekt.shared.report.SimpleParagraph;
import de.hdm_stuttgart.huber.itprojekt.shared.report.SimpleReport;



/**
 * Implementierung des <code>ReportGenerator</code>-Interface. Die technische
 * Realisierung bzgl. <code>RemoteServiceServlet</code> bzw. GWT RPC erfolgt
 * analog zu {@lBankAdministrationImplImpl}. Für Details zu GWT RPC siehe dort.
 * 
 * @see ReportGenerator
 * @author thies
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet
    implements ReportGenerator {

  /**
   * Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese die
   * essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
   * bo-Package) bietet.
   */
  private Editor administration = null;

  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
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
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
   * 
   * @see #ReportGeneratorImpl()
   */
  @Override
public void init() throws IllegalArgumentException {
    /*
     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
     * BankVerwaltungImpl-Instanz.
     */
    EditorImpl a = new EditorImpl();
    a.init();
    this.administration = a;
  }

  /**
   * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
   * 
   * @return das BankVerwaltungsobjekt
   */
  protected Editor getNoteBookVerwaltung() {
    return this.administration;
  }

  /**
   * Setzen des zugehörigen Bank-Objekts.
   */
  @Override
public void create(UserInfo uI) {
    try {
		this.administration.create(uI);
	} catch (BullshitException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  /**
   * Hinzufügen des Report-Impressums. Diese Methode ist aus den
   * <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
   * diese Tätigkeiten redundant auszuführen hätte. Stattdessen rufen die
   * <code>create...</code>-Methoden diese Methode auf.
   * 
   * @param r der um das Impressum zu erweiternde Report.
   * 
   */

@Override
public AllUserNotebooksR createAllUserNotebooksR(UserInfo u) throws IllegalArgumentException {

Vector<NoteBook> allNoteBooks = NoteBookMapper.getNoteBookMapper().getAllNoteBooks();
	
	AllUserNotebooksR report = new AllUserNotebooksR();
	report.setTitle("Alle Notebooks aller User, d.h. alle Notebooks.");
	report.setCreated(new Date(System.currentTimeMillis()));
	
	StringBuilder sb = new StringBuilder();
	sb.append("Anzahl der Notebooks:");
	sb.append(allNoteBooks.size());
	report.setHeaderData(new SimpleParagraph(sb.toString()));
	
	Row headline = new Row();
	headline.addColumn((new Column("Title")));
	headline.addColumn((new Column("Subtitle")));
	headline.addColumn((new Column("Username")));
	headline.addColumn((new Column("Creation Date")));
	headline.addColumn((new Column("Modification Date")));
	report.addRow(headline);
	
	for (NoteBook element : allNoteBooks) {
		
		Row r = new Row();
		r.addColumn(new Column(element.getTitle()));
		r.addColumn(new Column(element.getSubtitle()));
		r.addColumn(new Column(element.getOwner().getNickname()));
		r.addColumn(new Column(element.getCreationDate().toString()));
		r.addColumn(new Column(element.getModificationDate().toString()));
		
		report.addRow(r);
		
	}
	
	report.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));
	
	return report;
	
}

@Override
public AllNotebooksR createAllNotebooksR() throws IllegalArgumentException {
	
	Vector<NoteBook> allNoteBooks = NoteBookMapper.getNoteBookMapper().getAllNoteBooks();
	
	AllNotebooksR report2 = new AllNotebooksR();
	report2.setTitle("Alle Notebooks aller User, d.h. alle Notebooks.");
	report2.setCreated(new Date(System.currentTimeMillis()));
	
	StringBuilder sb = new StringBuilder();
	sb.append("Anzahl der Notebooks:");
	sb.append(allNoteBooks.size());
	report2.setHeaderData(new SimpleParagraph(sb.toString()));
	
	Row headline = new Row();
	headline.addColumn((new Column("Title")));
	headline.addColumn((new Column("Subtitle")));
	headline.addColumn((new Column("Username")));
	headline.addColumn((new Column("Creation Date")));
	headline.addColumn((new Column("Modification Date")));
	report2.addRow(headline);
	
	for (NoteBook element : allNoteBooks) {
		
		Row r = new Row();
		r.addColumn(new Column(element.getTitle()));
		r.addColumn(new Column(element.getSubtitle()));
		r.addColumn(new Column(element.getOwner().getNickname()));
		r.addColumn(new Column(element.getCreationDate().toString()));
		r.addColumn(new Column(element.getModificationDate().toString()));
		
		report2.addRow(r);
		
	}
	
	report2.setImprint(new SimpleParagraph("Lorem ipsum sit dolor amet"));
	
	return report2;
}

@Override
public AllUserNotesR createAllUserNotesR(UserInfo u) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AllNotesR createAllNotesR() throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AllUserPermissionsR createAllUserPermissionsR(UserInfo u) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AllPermissionsR createAllPermissionsR() throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

}
