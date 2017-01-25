package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

/**
 * <p>
 * Basierend auf einem Showcase aus dem Bankprojekt.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * </p>
 *
 * @author thies
 *
 */
public class ReportGeneratorMenu extends VerticalPanel {

    ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
    EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

    private Anchor filter = new Anchor("Custom Reports");
    private Anchor showAllNotebooks = new Anchor("All Notebooks");
    private Anchor showAllNotes = new Anchor("All Notes");
    private Anchor showAllPermissions = new Anchor("All Permissions");
    private Anchor home;

    private PopupPanel loadingPanel;

    @Override
	protected void onLoad() {

        this.setHorizontalAlignment(ALIGN_CENTER);

        intializeLoadingPopup();

        home = new Anchor("Home", GWT.getHostPageBaseURL() + "Report.html");

        setStylesAndAddToPanel();

        setUpClickHandlers();

    }

    private void intializeLoadingPopup() {

        loadingPanel = new PopupPanel(false, true);
        HTML h = new HTML("Report wird geladen");
        h.setStyleName("loadingpanel");
        loadingPanel.setWidget(h);
        int left = (int) (Window.getClientWidth() * 0.5);
        int top = (int) (Window.getClientHeight() * 0.5);
        loadingPanel.setPopupPosition(left, top);

    }

    private void setStylesAndAddToPanel() {

        home.setStyleName("pure-menu-heading");
        home.getElement().getStyle().setColor("#ffffff");
        this.add(home);

        filter.setStyleName("pure-menu-link");
        this.add(filter);

        showAllNotebooks.setStyleName("pure-menu-link");
        this.add(showAllNotebooks);

        showAllNotes.setStyleName("pure-menu-link");
        this.add(showAllNotes);

        showAllPermissions.setStyleName("pure-menu-link");
        this.add(showAllPermissions);
    }

    private void setUpClickHandlers() {

        filter.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                ApplicationPanel.getApplicationPanel().replaceContentWith(new FilterAbleReports());

            }

        });

        showAllNotebooks.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                loadingPanel.show();
                reportGenerator.createAllNotebooksR(new GenericReportCallback<AllNotebooksR>());

            }

        });

        showAllNotes.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                loadingPanel.show();
                reportGenerator.createAllNotesR(new GenericReportCallback<AllNotesR>());

            }

        });


        showAllPermissions.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                loadingPanel.show();
                reportGenerator.createAllPermissionsR(new GenericReportCallback<AllPermissionsR>());

            }

        });
    }

    private void printSimpleReport(SimpleReport r) {

        HTMLReportWriter writer = new HTMLReportWriter();
        String html = writer.simpleReport2HTML(r);
        HTML htmlToDisplay = new HTML(html);
        ScrollPanel panel = new ScrollPanel(htmlToDisplay);
        panel.setSize("100%", "100%");

        ApplicationPanel.getApplicationPanel().replaceContentWith(panel);

    }

    private class GenericReportCallback<T> implements AsyncCallback<T> {

        @Override
        public void onFailure(Throwable caught) {

            loadingPanel.hide();
            GWT.log(caught.toString());
            Notificator.getNotificator().showError("Report could not be generated");

        }

        @Override
        public void onSuccess(T result) {

            loadingPanel.hide();

            if (result instanceof SimpleReport) {
                SimpleReport r = (SimpleReport) result;
                printSimpleReport(r);
            }
        }
    }

}