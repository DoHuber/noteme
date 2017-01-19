package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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

	private UserInfo userInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the cool and nice application.");
	private Anchor signInLink = new Anchor("Sign In");

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				GWT.log(e.toString());
			}

		});

		SharedServicesAsync loginService = GWT.create(SharedServices.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {

			public void onFailure(Throwable e) {

			}

			public void onSuccess(UserInfo result) {

				userInfo = result;

				if (userInfo.isLoggedIn()) {

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

	





		ShowReportDemo navigation = new ShowReportDemo();
		ShowReportDemo.setLogOutUrl(userInfo.getLogoutUrl());
		RootPanel.get("menu").add(navigation);

	}

	private void loadLogin() {

		signInLink.setHref(userInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("menu").add(loginPanel);

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
