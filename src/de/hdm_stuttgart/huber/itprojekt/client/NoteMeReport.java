package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllUserPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;

/**
 * Entry-Point-Klasse des Projekts <b>BankProjekt</b>.
 */
public class NoteMeReport implements EntryPoint {

	ReportGeneratorAsync reportGenerator;

	private UserInfo loggedInUser;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the cool and nice application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	private ApplicationPanel applicationPanel;


	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {
		
		applicationPanel = ApplicationPanel.getApplicationPanel();
		RootLayoutPanel.get().add(applicationPanel);

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				GWT.log(e.toString());
			}

		});

		SharedServicesAsync loginService = GWT.create(SharedServices.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {

			@Override
			public void onFailure(Throwable e) {
				GWT.log(e.toString());
			}

			@Override
			public void onSuccess(UserInfo result) {

				loggedInUser = result;

				if (loggedInUser.isLoggedIn()) {

					loadMenu();

				} else {
					
					loadLogin();
					
				}

			}

		});

	}

	private void loadMenu() {

		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}

		ReportGeneratorMenu navigation = new ReportGeneratorMenu();
		applicationPanel.setNavigation(navigation);
		
		setUpHeaderPanel();
		
		Label n = new Label("Gruppe 7, IT-Projekt HdM Stuttgart");
		applicationPanel.setFooter(n);

	}

	private void setUpHeaderPanel() {
		
		HorizontalPanel headerPanel = new HorizontalPanel();
		final Button accountButton = new Button(IconConstants.ICON_ACCOUNT_CIRCLE);

		headerPanel.setStyleName("headerpanel");
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		headerPanel.add(new HTML("<img width=\"33%\" src=\"LeftUpperSprite.jpg\" alt=\"Fehler\">"));

		Label l = new Label("NoteMe");
		l.setStyleName("headerlabel");
		headerPanel.add(l);

		accountButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				openAccountPanel(accountButton);
			}

		});

		headerPanel.add(accountButton);

		headerPanel.setCellWidth(headerPanel.getWidget(0), "33%");
		headerPanel.setCellWidth(headerPanel.getWidget(1), "33%");
		headerPanel.setCellWidth(headerPanel.getWidget(2), "33%");

		applicationPanel.setHeader(headerPanel);
		
	}

	private void openAccountPanel(Button accountButton) {

		AccountPanel ap = new AccountPanel(loggedInUser);
		ap.setPopupPosition(accountButton.getAbsoluteLeft(), accountButton.getAbsoluteTop());
		ap.show();

	}

	private void loadLogin() {

		signInLink.setHref(loggedInUser.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		
		ApplicationPanel.getApplicationPanel().replaceContentWith(loginPanel);

	}

	/**
	 * Diese Nested Class wird als Callback für das Erzeugen des
	 * AllAccountOfAllCustomersReport benötigt.
	 * 
	 * @author rathke
	 * @version 1.0
	 */
	class createAllNotebooksRCallback implements AsyncCallback<AllNotebooksR> {

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
		public void onSuccess(AllNotebooksR report) {

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

	class createAllNotesRCallback implements AsyncCallback<AllNotesR> {

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
		public void onSuccess(AllNotesR report) {
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

	class createAllUserNotesRCallback implements AsyncCallback<AllUserNotesR> {

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
		public void onSuccess(AllUserNotesR report) {
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

	class createAllPermissionsRCallback implements AsyncCallback<AllPermissionsR> {

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
		public void onSuccess(AllPermissionsR report) {
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

	class createAllUserPermissionsRCallback implements AsyncCallback<AllUserPermissionsR> {

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
		public void onSuccess(AllUserPermissionsR report) {
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

}
