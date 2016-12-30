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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;

/**
 * Entry-Point-Klasse des Projekts <b>BankProjekt</b>.
 */
public class NoteMeReport implements EntryPoint {

	ReportGeneratorAsync reportGenerator;
	Button showReportButton;
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

		showReportButton = new Button("Irgendwas anziegen ahh!!");

		GWT.log(reportGenerator.toString());
		GWT.log(showReportButton.toString());

		showReportButton.setStylePrimaryName("bankproject-menubutton");
		showReportButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				GWT.log("Onclick fired!");
				reportGenerator.createAllNotebooksR(new createAllNotebooksRCallback());

			}

		});

		RootPanel.get("menu").add(showReportButton);

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

}
