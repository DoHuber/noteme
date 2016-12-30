package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.NoteMeReport.createAllNotebooksRCallback;
import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
import de.hdm_stuttgart.huber.itprojekt.client.gui.UnorderedListWidget;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
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
	
	
	ReportGeneratorAsync reportGenerator;
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
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
			
	
			
			
			showUserNotebooks.addClickHandler(new ShowAllUserNotebooksHandler());
			showAllNotebooks.addClickHandler(new ShowAllNotebooksHandler());
			showUserNotes.addClickHandler(new ShowAllUserNotesHandler());
			showAllNotes.addClickHandler(new ShowAllNotesHandler());
			showUserPermissions.addClickHandler(new ShowAllUserPermissions());
			showAllPermissions.addClickHandler(new ShowAllPermissionsHAndler());
			

	}
	
	
	
	private class ShowAllUserNotebooksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ShowReportDemo sRd = new ShowReportDemo();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(sRd);

			reportGenerator.createAllUserNotebooksR(null, new createAllUserNotebooksRCallback());
			RootPanel.get("main").clear();
		}
	}
	class createAllUserNotebooksRCallback implements AsyncCallback<AllUserNotebooksR> {

		@Override
		public void onFailure(Throwable caught) {
			/*
			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message
			 * aus.
			 */
			GWT.log("Erzeugen des Reports fehlgeschlagen!");
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(AllUserNotebooksR report) {

			GWT.log("onSuccess reached!");
			GWT.log(report.toString());

			if (report != null) {

				HTMLReportWriter writer = new HTMLReportWriter();
				String html = writer.simpleReport2HTML(report);
				
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new HTML(html));
				
			}
		}

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


  @Override
  protected void run() {
    this.append("Auslesen des Kunden mit Kd.-Nr. 1.");

    EditorAsync administration = ClientsideSettings.getEditorVerwaltung();

    administration.getUserById(1, new GetUserCallback(this));
    RootPanel.get("menu").clear();
  }


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