package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

/**
 * Entry-Point-Klasse des Projekts <b>BankProjekt</b>.
 */
public class NoteMeReport implements EntryPoint {

    ReportGeneratorAsync reportGenerator;

    private UserInfo loggedInUser;
    private VerticalPanel loginPanel = new VerticalPanel();
    private Label loginLabel = new Label(
            "Please sign in to your Google AccountEditView to access the cool and nice application.");
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
        loginService.login(GWT.getHostPageBaseURL() + "Report.html", new AsyncCallback<UserInfo>() {

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
		
		if (!loggedInUser.isAdmin()) {
			
			Notificator.getNotificator().showError("Admins only!");
			VerticalPanel vp = new VerticalPanel();
			
			vp.setWidth("100%");
			vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			
			vp.add(new Label("Admin-only feature: Either sign out to resign as admin, or switch to the main application:"));
			Anchor a = new Anchor("Sign out");
			a.setHref(loggedInUser.getLogoutUrl());
			Anchor b = new Anchor("Main Application");
			b.setHref(GWT.getHostPageBaseURL() + "IT_Projekt.html");
			
			vp.add(a);
			vp.add(b);
			
			ApplicationPanel.getApplicationPanel().replaceContentWith(vp);
			
			return;
			
		}

        if (reportGenerator == null) {
            reportGenerator = ClientsideSettings.getReportGenerator();
        }

        ReportGeneratorMenu navigation = new ReportGeneratorMenu();
        applicationPanel.setNavigation(navigation);

        setUpHeaderPanel();

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Label("Gruppe 7: Hochschule der Medien"));
        Anchor a = new Anchor("NoteMe - Hauptapplikation");
        a.setHref(GWT.getHostPageBaseURL() + "IT_Projekt.html");
        hp.add(a);

        hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        applicationPanel.setFooter(hp);

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
