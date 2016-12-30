package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
import de.hdm_stuttgart.huber.itprojekt.client.gui.UnorderedListWidget;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Anzeigen eines Reports zum
 * Kunden mit der Kundennummer 1. Demonstration der Nutzung des Report
 * Generators.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * {@link CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies
 * @version 1.0
 * 
 */
public class ShowReportDemo extends MenuView {
	
	//private static String logOutUrl;
	//private Anchor logoutAnchor;

	protected void onLoad() {
		
		FlowPanel menu  = new FlowPanel();
		FlowPanel pureMenu  = new FlowPanel();
		UnorderedListWidget menuList = new UnorderedListWidget();
		
		Anchor home = new Anchor("Home", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		Anchor showUserNotebooks = new Anchor("UserNotebooks");
		Anchor showAllNotebooks = new Anchor ("AllNotebooks");
		Anchor showUserNotes = new Anchor("UserNotes");
		Anchor showAllNotes = new Anchor("AllNotes");
		Anchor showUserPermissions = new Anchor("UserPermissions");
		Anchor showAllPermissions = new Anchor("AllPermissions");

		showUserNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showUserNotebooks));
		
		showAllNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllNotebooks));
		
		showUserNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showUserNotes));
		
		showAllNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllNotes));
		
		showUserPermissions.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showUserPermissions));
		
		showAllPermissions.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllPermissions));
		
			pureMenu.add(home);
			pureMenu.add(menuList);
			menu.add(pureMenu);
			RootPanel.get("menu").add(menu);
			
//			showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
//	}
//
//	private class CreateNotebookHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			MenuView mView = new MenuView();
//			RootPanel.get("menu").clear();
//			RootPanel.get("menu").add(mView);
//
//			CreateNotebook cN = new CreateNotebook();
//			RootPanel.get("main").clear();
//			RootPanel.get("main").add(cN);
//		}
	}
  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  protected String getHeadlineText() {
    return "Show Report";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Auslesen des Kunden mit Kd.-Nr. 1.");

    EditorAsync administration = ClientsideSettings.getEditorVerwaltung();

    administration.getUserById(1, new GetUserCallback(this));
    RootPanel.get("menu").clear();
  }

  /**
   * <p>
   * Wir nutzen eine Nested Class, um das zurückerhaltene Objekt weiter zu
   * bearbeiten.
   * </p>
   * <p>
   * <b>Amerkungen:</b> Eine Nested Class besitzt den Vorteil, die Lokalität des
   * Gesamtsystems zu fördern, da der Klassenname (hier: "UseCustomer")
   * außerhalb von DeleteAccountDemo nicht "verbraucht" wird. Doch Vorsicht!
   * Wenn eine Klasse mehrfach, also gewissermaßen an mehreren Stellen im
   * Programm, nutzbar ist, sollte man überlegen, ob man eine solche Klasse als
   * normale - also separate - Klasse realisiert bzw. anordnet.
   * </p>
   * <p>
   * Weitere Dokumentation siehe <code>CreateAccountDemo.UseCustomer</code>.
   * </p>
   * 
   * @see CreateAccountDemo.UseCustomer
   */
  class GetUserCallback implements AsyncCallback<UserInfo> {
    private MenuView mView = null;

    public GetUserCallback(MenuView c) {
      this.mView = c;
    }

    public void onFailure(Throwable caught) {
      this.mView.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(UserInfo user) {
      if (user != null) {
        ReportGeneratorAsync reportGenerator = ClientsideSettings
            .getReportGenerator();
        
        reportGenerator.createAllUserNotebooksR(user,
            new AllAccountsOfCustomerReportCallback(this.mView));
      }
    }

    /**
     * <p>
     * Diese Klasse ist eine Nested Classs innerhalb einer Nested Class! Auf
     * diese Weise können wir einen klassenbezogenen Verarbeitungskontext
     * aufbauen, also gewissermaßen einen klassenbasierter Stack.
     * </p>
     * <p>
     * <b>Erläuterung:</b> Stellen Sie sich folgende Struktur vor (Syntax frei
     * erfunden):
     * 
     * <pre>
     * (Instance of GetCustomerCallback
     * 
     *    Hier sind sämtliche Infos zum Kontext nach dem ersten Call bzgl. 
     *    des Kunden verfügbar, also als Ergebnis des Calls das Kundenobjekt.
     *    
     *    (Instance of AllAccountsOfCustomerReportCallback
     *    
     *       Hier sind zusätzlich noch die Infos zum Kontext nach dem zweiten 
     *       Call, also der fertige Report zu dessen Weiterverarbeitung, verfügbar.
     *    
     *    )
     * )
     * </pre>
     * 
     * </p>
     * 
     * @author thies
     * @version 1.0
     * 
     */
    class AllAccountsOfCustomerReportCallback
        implements AsyncCallback<AllUserNotebooksR> {
      private MenuView mView = null;

      public AllAccountsOfCustomerReportCallback(MenuView c) {
        this.mView = c;
      }

      @Override
      public void onFailure(Throwable caught) {
        this.mView.append("Fehler bei der Abfrage " + caught.getMessage());
      }

      @Override
      public void onSuccess(AllUserNotebooksR report) {
        if (report != null) {
          HTMLReportWriter writer = new HTMLReportWriter();
          writer.process(report);
          this.mView.append(writer.getReportText());
        }
      }
    }
  }

}