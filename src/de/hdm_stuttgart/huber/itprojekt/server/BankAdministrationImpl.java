package de.hdm_stuttgart.huber.itprojekt.server;

import java.util.ArrayList;
import java.util.Vector;


import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.SourceMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.BullshitException;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * <p>
 * Implementierungsklasse des Interface <code>BankAdministration</code>. Diese
 * Klasse ist <em>die</em> Klasse, die neben {@link ReportGeneratorImpl}
 * sämtliche Applikationslogik (oder engl. Business Logic) aggregiert. Sie ist
 * wie eine Spinne, die sämtliche Zusammenhänge in ihrem Netz (in unserem Fall
 * die Daten der Applikation) überblickt und für einen geordneten Ablauf und
 * dauerhafte Konsistenz der Daten und Abläufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * lässt schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgeführt werden, die das System
 * von einem konsistenten Zustand in einen anderen, auch wieder konsistenten
 * Zustand überführen. Wenn dies zwischenzeitig scheitern sollte, dann ist das
 * jeweilige Transaction Script dafür verwantwortlich, eine Fehlerbehandlung
 * durchzuführen.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link BankAdministration}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfügung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link BankAdministrationAsync}: <code>BankVerwaltungImpl</code> und
 * <code>BankAdministration</code> bilden nur die Server-seitige Sicht der
 * Applikationslogik ab. Diese basiert vollständig auf synchronen
 * Funktionsaufrufen. Wir müssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusätzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstützt. Weitere Informationen unter
 * {@link BankAdministrationAsync}.</li>
 * <li> {@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig über GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis für die Anbindung von <code>BankVerwaltungImpl</code> an die Runtime
 * des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehören der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(Customer)} Einzig nachvollziehbares
 * Argument für einen solchen Ansatz ist die Steigerung der Performance
 * umfangreicher Datenbankoperationen. Doch auch dieses Argument zieht nur dann,
 * wenn wirklich große Datenmengen zu handhaben sind. In einem solchen Fall
 * würde man jedoch eine entsprechend erweiterte Architektur realisieren, die
 * wiederum sämtliche Applikationslogik in der Applikationsschicht isolieren
 * würde. Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern
 * dies auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass sämtliche Methoden, die mittels GWT RPC aufgerufen werden
 * können ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden dürfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions können z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 * <p>
 * Es gibt sicherlich noch viel mehr über diese Klasse zu schreiben. Weitere
 * Infos erhalten Sie in der Lehrveranstaltung.
 * </p>
 * 
 * @see BankAdministration
 * @see BankAdministrationAsync
 * @see RemoteServiceServlet
 * @author Thies
 */
@SuppressWarnings("serial")
public class BankAdministrationImpl extends RemoteServiceServlet
    implements Editor {

  /**
   * Wie lautet die Standardkontonummer für das Kassenkonto der Bank?
   */
  public static final int DEFAULT_CASH_ACCOUNT_ID = 10000;

  /**
   * Referenz auf den DatenbankMapper, der Kundenobjekte mit der Datenbank
   * abgleicht.
   */
  private UserInfoMapper uIMapper = null;

  /**
   * Referenz auf den DatenbankMapper, der Kontoobjekte mit der Datenbank
   * abgleicht.
   */
  private NoteBookMapper nbMapper = null;

  /**
   * Referenz auf den TransactionMapper, der Buchungsobjekte mit der Datenbank
   * abgleicht.
   */
  private NoteMapper nMapper = null;

  private PermissionMapper pMapper = null;
  
  private SourceMapper sMapper = null;
  /*
   * Da diese Klasse ein gewisse Größe besitzt - dies ist eigentlich ein
   * Hinweise, dass hier eine weitere Gliederung sinnvoll ist - haben wir zur
   * besseren Übersicht Abschnittskomentare eingefügt. Sie leiten ein Cluster in
   * irgeneinerweise zusammengehöriger Methoden ein. Ein entsprechender
   * Kommentar steht am Ende eines solchen Clusters.
   */

private UserInfoMapper getUserInfoMapper;

private NoteBookMapper getNoteBookMapper;

private NoteMapper getNoteMapper;

private PermissionMapper getPermissionMapper;

private SourceMapper getSourceMapper;

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Initialisierung
   * ***************************************************************************
   */
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
   * 
   * @see #init()
   */
  public BankAdministrationImpl() throws IllegalArgumentException {
    /*
     * Eine weitergehende Funktion muss der No-Argument-Constructor nicht haben.
     * Er muss einfach vorhanden sein.
     */
  }

  /**
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor
   * {@link #ReportGeneratorImpl()}. Diese Methode muss für jede Instanz von
   * <code>BankVerwaltungImpl</code> aufgerufen werden.
   * 
   * @see #ReportGeneratorImpl()
   */
  @Override
public void init() throws IllegalArgumentException {
    /*
     * Ganz wesentlich ist, dass die BankAdministration einen vollständigen Satz
     * von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
     * kommunizieren kann.
     */
		this.getUserInfoMapper = UserInfoMapper.getUserInfoMapper();
		this.getNoteBookMapper = NoteBookMapper.getNoteBookMapper();

  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Initialisierung
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Customer-Objekte
   * ***************************************************************************
   */
  /**
   * <p>
   * Anlegen eines neuen Kunden. Dies führt implizit zu einem Speichern des
   * neuen Kunden in der Datenbank.
   * </p>
   * 
   * <p>
   * <b>HINWEIS:</b> Änderungen an Customer-Objekten müssen stets durch Aufruf
   * von {@link #save(Customer c)} in die Datenbank transferiert werden.
   * </p>
   * 
   * @see save(Customer c)
   */
  @Override
public Customer createCustomer(String first, String last)
      throws IllegalArgumentException {
    Customer c = new Customer();
    c.setFirstName(first);
    c.setLastName(last);

    /*
     * Setzen einer vorläufigen Kundennr. Der insert-Aufruf liefert dann ein
     * Objekt, dessen Nummer mit der Datenbank konsistent ist.
     */
    c.setId(1);

    // Objekt in der DB speichern.
    return this.cMapper.insert(c);
  }

  /**
   * Auslesen aller Kunden, die den übergebenen Nachnamen besitzen.
   */
  @Override
public Vector<Customer> getCustomerByName(String lastName)
      throws IllegalArgumentException {

    return this.cMapper.findByLastName(lastName);
  }

  /**
   * Auslesen eines Kunden anhand seiner Kundennummer.
   */
  @Override
public Customer getCustomerById(int id) throws IllegalArgumentException {
    return this.cMapper.findByKey(id);
  }

  /**
   * Auslesen aller Kunden.
   */
  @Override
public Vector<Customer> getAllCustomers() throws IllegalArgumentException {
    return this.cMapper.findAll();
  }

  /**
   * Speichern eines Kunden.
   */
  @Override
public void save(Customer c) throws IllegalArgumentException {
    cMapper.update(c);
  }

  /**
   * Löschen eines Kunden. Natürlich würde ein reales System zur Verwaltung von
   * Bankkunden ein Löschen allein schon aus Gründen der Dokumentation nicht
   * bieten, sondern deren Status z.B von "aktiv" in "ehemalig" ändern. Wir
   * wollen hier aber dennoch zu Demonstrationszwecken eine Löschfunktion
   * vorstellen.
   */
  @Override
public void delete(Customer c) throws IllegalArgumentException {
    /*
     * Zunächst werden sämtl. Konten des Kunden aus der DB entfernt.
     * 
     * Beachten Sie, dass wir dies auf Ebene der Applikationslogik, konkret: in
     * der Klasse BankVerwaltungImpl, durchführen. Grund: In der Klasse
     * BankVerwaltungImpl ist die Verflechtung sämtlicher Klassen bzw. ihrer
     * Objekte bekannt. Nur hier kann sinnvoll ein umfassender Verwaltungsakt
     * wie z.B. dieser Löschvorgang realisiert werden.
     * 
     * Natürlich könnte man argumentieren, dass dies auch auf Datenbankebene
     * (sprich: mit SQL) effizienter möglich ist. Das Gegenargument ist jedoch
     * eine dramatische Verschlechterung der Wartbarkeit Ihres Gesamtsystems
     * durch einen zu niedrigen Abstraktionsgrad und der Verortung von Aufgaben
     * an einer Stelle (Datenbankschicht), die die zuvor genannte Verflechtung
     * nicht umfänglich kennen kann.
     */
    Vector<Account> accounts = this.getAccountsOf(c);

    if (accounts != null) {
      for (Account a : accounts) {
        this.delete(a);
      }
    }

    // Anschließend den Kunden entfernen
    this.cMapper.delete(c);
  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Customer-Objekte
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Account-Objekte
   * ***************************************************************************
   */
  /**
   * Auslesen sämtlicher Konten dieses Systems.
   */
  @Override
public Vector<Account> getAllAccounts() throws IllegalArgumentException {
    return this.aMapper.findAll();
  }

  /**
   * Auslesen aller Konten des übergeben Kunden.
   */
  @Override
public Vector<Account> getAccountsOf(Customer c)
      throws IllegalArgumentException {
    return this.aMapper.findByOwner(c);
  }
  
  /**
   * Auslesen des Kontos mit einer bestimmten Id
   */
  @Override
public Account getAccountById(int id) throws IllegalArgumentException {
	  return aMapper.findByKey(id);
  }

  /**
   * Löschen des übergebenen Kontos. Beachten Sie bitte auch die Anmerkungen zu
   * {@link #delete(Customer)}. Beim Löschen des Kontos werden sämtliche damit
   * in Verbindung stehenden Buchungen gelöscht.
   * 
   * @see #delete(Customer)
   */
  @Override
public void delete(Account a) throws IllegalArgumentException {
    /*
     * Zunächst werden sämtl. Buchungen des Kunden aus der DB entfernt.
     */
    ArrayList<Transaction> debits = this.getDebitsOf(a);
    ArrayList<Transaction> credits = this.getCreditsOf(a);

    if (debits != null) {
      for (Transaction t : debits) {
        this.delete(t);
      }
    }

    if (credits != null) {
      for (Transaction t : credits) {
        this.delete(t);
      }
    }

    // Account aus der DB entfernen
    this.aMapper.delete(a);
  }

  /**
   * Anlegen eines neuen Kontos für den übergebenen Kunden. Dies führt implizit
   * zu einem Speichern des neuen, leeren Kontos in der Datenbank.
   * <p>
   * 
   * <b>HINWEIS:</b> Änderungen an Account-Objekten müssen stets durch Aufruf
   * von {@link #save(Account)} in die Datenbank transferiert werden.
   * 
   * @see save(Account a)
   */
  @Override
public Account createAccountFor(Customer c) throws IllegalArgumentException {
    Account a = new Account();
    a.setOwnerID(c.getId());

    /*
     * Setzen einer vorläufigen Kontonr. Der insert-Aufruf liefert dann ein
     * Objekt, dessen Nummer mit der Datenbank konsistent ist.
     */
    a.setId(1);

    // Objekt in der DB speichern.
    return this.aMapper.insert(a);
  }

  /**
   * <p>
   * Ausgeben des Kontostands des übergebenen Kontos. Dieser wird durch ein
   * gegeneinander Aufrechnen von Zubuchungen und Abbuchungen auf Basis von
   * {@link Transaction}-Instanzen bestimmt.
   * </p>
   * 
   * @param k das Konto, dessen Stand wir berechnen möchten
   */
  @Override
public float getBalanceOf(Account k) throws IllegalArgumentException {
    float creditAmount = 0.0f;
    float debitAmount = 0.0f;

    ArrayList<Transaction> credits = this.getCreditsOf(k);
    ArrayList<Transaction> debits = this.getDebitsOf(k);

    if (credits != null) {
      for (Transaction b : credits) {
        creditAmount += b.getAmount();
      }
    }

    if (debits != null) {
      for (Transaction b : debits) {
        debitAmount += b.getAmount();
      }
    }

    return creditAmount - debitAmount;
  }

  /**
   * <p>
   * Auslesen sämtlicher mit diesem Konto in Verbindung stehenden
   * Soll-Buchungen. Diese Methode wird in {@link #getBalanceOf(Account)}
   * verwendet.
   * </p>
   * 
   * @param k das Konto, dessen Soll-Buchungen wir bekommen wollen.
   * @return eine Liste aller Soll-Buchungen
   * @throws IllegalArgumentException
   */
  @Override
public ArrayList<Transaction> getDebitsOf(Account k)
      throws IllegalArgumentException {
    ArrayList<Transaction> result = new ArrayList<Transaction>();

    if (k != null && this.tMapper != null) {
      Vector<Transaction> transactions = this.tMapper.findBySourceAccount(k
          .getId());
      if (transactions != null) {
        result.addAll(transactions);
      }
    }

    return result;
  }

  /**
   * <p>
   * Auslesen sämtlicher mit diesem Konto in Verbindung stehenden
   * Haben-Buchungen. Diese Methode wird in {@link #getBalanceOf(Account)}
   * verwendet.
   * </p>
   * 
   * @param k das Konto, dessen Haben-Buchungen wir bekommen wollen.
   * @return eine Liste aller Haben-Buchungen
   * @throws IllegalArgumentException
   */
  @Override
public ArrayList<Transaction> getCreditsOf(Account k)
      throws IllegalArgumentException {
    ArrayList<Transaction> result = new ArrayList<Transaction>();

    if (k != null && this.tMapper != null) {
      Vector<Transaction> transactions = this.tMapper.findByTargetAccount(k
          .getId());
      if (transactions != null) {
        result.addAll(transactions);
      }
    }

    return result;
  }

  /**
   * Speichern eines Kontos.
   */
  @Override
public void save(Account a) throws IllegalArgumentException {
    aMapper.update(a);
  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Account-Objekte
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Transaction-Objekte
   * ***************************************************************************
   */
  /**
   * Erstellen einer neuen Buchung.
   * 
   * @param source das Quellkonto
   * @param target das Zielkonto
   * @param value der Geldwert dieser Buchung
   */
  @Override
public Transaction createTransactionFor(Account source, Account target,
      float value) throws IllegalArgumentException {

    /*
     * Wir legen eine neue, leere Buchung an.
     */
    Transaction t = new Transaction();

    /*
     * Setzen einer vorläufigen Kontonr. Der insert-Aufruf liefert dann ein
     * Objekt, dessen Nummer mit der Datenbank konsistent ist.
     */
    t.setId(1);

    /*
     * Setzen des Quell- und des Zielkontos.
     */
    t.setSourceAccountID(source.getId());
    t.setTargetAccountID(target.getId());

    /*
     * Setzen des Geldwerts, der von Quellkonto zum Zielkontos z.B als Folge
     * einer Überweisung gebucht werden soll.
     */
    t.setAmount(value);

    // Objekt in der DB speichern.
    return this.tMapper.insert(t);
  }

  /**
   * Löschen der übergebenen Buchung. Beachten Sie bitte auch die Anmerkungen zu
   * {@link #delete(Customer)} und {@link #delete(Account)}.
   * 
   * @see #delete(Customer)
   * @see #delete(Account)
   */
  @Override
public void delete(Transaction t) throws IllegalArgumentException {
    this.tMapper.delete(t);
  }

  /**
   * Auslesen des Kassenkontos der Bank.
   * 
   * @return das Kassenkonto der Bank
   */
  private Account getCashAccount() {
    Account account = aMapper.findByKey(DEFAULT_CASH_ACCOUNT_ID);
    /* wenn das Kassenkonto nicht gefunden wird, erzeugen wir es mit der entsprechenden
     * Kontonummer und dem Wert 0 als ownerID.
     */
    if (account == null) {
    	account = new Account();
    	account.setId(DEFAULT_CASH_ACCOUNT_ID);
    	account.setOwnerID(0);
    	save(account);
    	}
    return account;
  }

  /**
   * Hier wird der Use Case abgebildet, dass der Kunde eine <b>Barabhebung</b>
   * von einem Konto machen möchte. Es wird hierdurch ein Buchungssatz
   * 
   * <pre>
   * Kundenkonto <Betrag>
   * an Kasse <Betrag>
   * </pre>
   * 
   * realisiert.
   * 
   * @param customerAccount das Kundenkonto, von dem die Barabhebung erfolgen
   *          soll.
   * @param amount der Betrag, der abgehoben werden soll.
   * @return Ein <code>Transaction</code>-Objekt, das den resultierenden
   *         Buchungssatz darstellt.
   */
  @Override
public Transaction createWithdrawal(Account customerAccount, float amount) {
    Account cashAccount = this.getCashAccount();
    if (cashAccount != null) {
      Transaction trans = this.createTransactionFor(customerAccount,
          cashAccount, amount);
      return trans;
    }
    else {
      return null;
    }
  }

  /**
   * Hier wird der Use Case abgebildet, dass der Kunde eine <b>Bareinzahlung</b>
   * auf einem Konto machen möchte. Es wird hierdurch ein Buchungssatz
   * 
   * <pre>
   * Kasse <Betrag>
   * an Kundenkonto <Betrag>
   * </pre>
   * 
   * realisiert.
   * 
   * @param customerAccount das Kundenkonto, auf das der Betrag eingezahlt
   *          werden soll.
   * @param amount der Betrag, der eingezahlt wird.
   * @return Ein <code>Transaction</code>-Objekt, das den resultierenden
   *         Buchungssatz darstellt.
   */
  @Override
public Transaction createDeposit(Account customerAccount, float amount) {
    Account cashAccount = this.getCashAccount();
    if (cashAccount != null) {
      Transaction trans = this.createTransactionFor(cashAccount,
          customerAccount, amount);
      return trans;
    }
    else {
      return null;
    }
  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Transaction-Objekte
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Verschiedenes
   * ***************************************************************************
   */
  /**
   * Auslesen der Bank für die diese Bankverwaltung gewissermaßen tätig ist.
   */
  @Override
public Bank getBank() throws IllegalArgumentException {
    return this.bank;
  }

  /**
   * Setzen der Bank für die diese Bankverwaltung tätig ist.
   */
  @Override
public void setBank(Bank b) throws IllegalArgumentException {
    this.bank = b;
  }
  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Verschiedenes
   * ***************************************************************************
   */

@Override
public String getHelloWorld() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NoteBook createNoteBook(NoteBook notebook) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NoteBook saveNoteBook(NoteBook noteBook) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NoteBook getNoteBookById(NoteBook notebook) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void deleteNoteBook(NoteBook noteBook) {
	// TODO Auto-generated method stub
	
}

@Override
public Note createNote(Note note) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Note saveNote(Note note) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Note getNoteById(Note note) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void deleteNote(Note note) {
	// TODO Auto-generated method stub
	
}

@Override
public Vector<Note> getAllNotesForCurrentUser() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Vector<NoteBook> getAllNoteBooksForCurrentUser() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Vector<NoteBook> getAllNoteBooks() throws BullshitException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Vector<Note> getAllNotes() throws BullshitException {
	// TODO Auto-generated method stub
	return null;
}

}