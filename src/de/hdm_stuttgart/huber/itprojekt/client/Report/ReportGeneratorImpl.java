package de.hdm_stuttgart.huber.itprojekt.client.Report;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.BankAdministrationImpl;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGenerator;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllAccountsOfAllCustomersReport;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllAccountsOfCustomerReport;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.Column;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.CompositeParagraph;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.Report;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.Row;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.SimpleParagraph;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;



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
    BankAdministrationImpl a = new BankAdministrationImpl();
    a.init();
    this.administration = a;
  }

  /**
   * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
   * 
   * @return das BankVerwaltungsobjekt
   */
  protected Editor getNoteVerwaltung() {
    return this.administration;
  }

  /**
   * Setzen des zugehörigen Bank-Objekts.
   */
  @Override
public void create(UserInfo uI) {
    this.administration.create(uI);
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
  
  
//  protected void addImprint(Report r) {
//    /*
//     * Das Impressum soll wesentliche Informationen über die Bank enthalten.
//     */
//    Bank bank = this.administration.getBank();
//
//    /*
//     * Das Imressum soll mehrzeilig sein.
//     */
//    CompositeParagraph imprint = new CompositeParagraph();
//
//    imprint.addSubParagraph(new SimpleParagraph(bank.getName()));
//    imprint.addSubParagraph(new SimpleParagraph(bank.getStreet()));
//    imprint.addSubParagraph(new SimpleParagraph(bank.getZip() + " "
//        + bank.getCity()));
//
//    // Das eigentliche Hinzufügen des Impressums zum Report.
//    r.setImprint(imprint);
//
//  }

  
  
  /**
   * Erstellen von <code>AllAccountsOfCustomerReport</code>-Objekten.
   * 
   * @param c das Kundenobjekt bzgl. dessen der Report erstellt werden soll.
   * @return der fertige Report
   */
  @Override
public AllAccountsOfCustomerReport createAllAccountsOfCustomerReport(
      Customer c) throws IllegalArgumentException {

    if (this.getBankVerwaltung() == null)
      return null;

    /*
     * Zunächst legen wir uns einen leeren Report an.
     */
    AllAccountsOfCustomerReport result = new AllAccountsOfCustomerReport();

    // Jeder Report hat einen Titel (Bezeichnung / Überschrift).
    result.setTitle("Alle Konten des Kunden");

    // Imressum hinzufügen
    this.addImprint(result);

    /*
     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
     */
    result.setCreated(new Date());

    /*
     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
     * die Verwendung von CompositeParagraph.
     */
    CompositeParagraph header = new CompositeParagraph();

    // Name und Vorname des Kunden aufnehmen
    header.addSubParagraph(new SimpleParagraph(c.getLastName() + ", "
        + c.getFirstName()));

    // Kundennummer aufnehmen
    header.addSubParagraph(new SimpleParagraph("Kd.-Nr.: " + c.getId()));

    // Hinzufügen der zusammengestellten Kopfdaten zu dem Report
    result.setHeaderData(header);

    /*
     * Ab hier erfolgt ein zeilenweises Hinzufügen von Konto-Informationen.
     */
    
    /*
     * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
     */
    Row headline = new Row();

    /*
     * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
     * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
     * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
     * Überschriften ab.
     */
    headline.addColumn(new Column("Kto.-Nr."));
    headline.addColumn(new Column("Kto.-Stand"));

    // Hinzufügen der Kopfzeile
    result.addRow(headline);

    /*
     * Nun werden sämtliche Konten des Kunden ausgelesen und deren Kto.-Nr. und
     * Kontostand sukzessive in die Tabelle eingetragen.
     */
    Vector<UserInfo> accounts = this.administration.getAccountsOf(c);

    for (Account a : accounts) {
      // Eine leere Zeile anlegen.
      Row accountRow = new Row();

      // Erste Spalte: Kontonummer hinzufügen
      accountRow.addColumn(new Column(String.valueOf(a.getId())));

      // Zweite Spalte: Kontostand hinzufügen
      accountRow.addColumn(new Column(String.valueOf(this.administration
          .getBalanceOf(a))));

      // und schließlich die Zeile dem Report hinzufügen.
      result.addRow(accountRow);
    }

    /*
     * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
     */
    return result;
  }

  /**
   * Erstellen von <code>AllAccountsOfAllCustomersReport</code>-Objekten.
   * 
   * @return der fertige Report
   */
  @Override
public AllAccountsOfAllCustomersReport createAllAccountsOfAllCustomersReport()
      throws IllegalArgumentException {

    if (this.getBankVerwaltung() == null)
      return null;

    /*
     * Zunächst legen wir uns einen leeren Report an.
     */
    AllAccountsOfAllCustomersReport result = new AllAccountsOfAllCustomersReport();

    // Jeder Report hat einen Titel (Bezeichnung / überschrift).
    result.setTitle("Alle Konten aller Kunden");

    // Imressum hinzufügen
    this.addImprint(result);

    /*
     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
     */
    result.setCreated(new Date());

    /*
     * Da AllAccountsOfAllCustomersReport-Objekte aus einer Sammlung von
     * AllAccountsOfCustomerReport-Objekten besteht, benötigen wir keine
     * Kopfdaten für diesen Report-Typ. Wir geben einfach keine Kopfdaten an...
     */

    /*
     * Nun müssen sämtliche Kunden-Objekte ausgelesen werden. Anschließend wir
     * für jedes Kundenobjekt c ein Aufruf von
     * createAllAccountsOfCustomerReport(c) durchgeführt und somit jeweils ein
     * AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
     * sukzessive der result-Variable hinzugefügt. Sie ist vom Typ
     * AllAccountsOfAllCustomersReport, welches eine Subklasse von
     * CompositeReport ist.
     */
    Vector<UserInfo> allCustomers = this.administration.getAllCustomers();

    for (UserInfo c : allCustomers) {
      /*
       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
       */
      result.addSubReport(this.createAllAccountsOfCustomerReport(c));
    }

    /*
     * Zu guter Letzt müssen wir noch den fertigen Report zurückgeben.
     */
    return result;
  }

@Override
public void setUser(UserInfo b) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
}

@Override
public AllAccountsOfCustomerReport createAllAccountsOfCustomerReport(UserInfo u) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AllAccountsOfAllCustomersReport createAllAccountsOfAllCustomersReport() throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

}
