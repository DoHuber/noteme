package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.report.CustomReport;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class FilterAbleReports extends BasicVerticalView {

    private final static String[] REPORT_CHOICES = {"notes", "notebooks", "permissions"};
    EditorAsync editor = ClientsideSettings.getEditorVerwaltung();
    private ListBox whatKindOfReport;
    private SuggestBox whichUser;
    private CheckBox includePermissions;
    private DateBox fromDate;
    private DateBox toDate;
    private MultiWordSuggestOracle oracle;
    private PopupPanel loadingPanel;
    public FilterAbleReports() {

    }

    @Override
    public String getHeadlineText() {

        return "Filter reports here";
    }

    @Override
    public String getSubHeadlineText() {

        return "Select from the Boxes below.";
    }

    @Override
    public void run() {

        whatKindOfReport = new ListBox();

        initializePopupPanel();

        for (String element : REPORT_CHOICES) {
            whatKindOfReport.addItem(element);
        }

        whatKindOfReport.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                String currentlySelected = whatKindOfReport.getValue(whatKindOfReport.getSelectedIndex());

                if (Objects.equals(currentlySelected, "permissions")) {

                    fromDate.setEnabled(false);
                    toDate.setEnabled(false);
                    includePermissions.setVisible(false);

                } else {

                    fromDate.setEnabled(true);
                    toDate.setEnabled(true);
                    includePermissions.setVisible(true);

                }
            }

        });

        whatKindOfReport.setVisibleItemCount(REPORT_CHOICES.length);

        oracle = new MultiWordSuggestOracle();
        whichUser = new SuggestBox(oracle);

        editor.getAllEmails(new AllMailCallback());

        Button startButton = new Button("Generate");
        startButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ApplicationPanel.getApplicationPanel().replaceContentWith(new Label(""));
                loadingPanel.show();
                getThaReport();
            }

        });

        HorizontalPanel alignPanel = new HorizontalPanel();
        alignPanel.setWidth("80%");
        alignPanel.setVerticalAlignment(ALIGN_MIDDLE);

        alignPanel.add(whatKindOfReport);

        VerticalPanel layoutPanel = new VerticalPanel();
        layoutPanel.add(new Label("Select a user or leave empty for all"));
        layoutPanel.add(whichUser);
        alignPanel.add(layoutPanel);

        fromDate = new DateBox();
        toDate = new DateBox();

        fromDate.setTitle("From what date");
        toDate.setTitle("To what date");

        VerticalPanel sandwich = new VerticalPanel();
        sandwich.add(new Label("Select from and to dates"));
        sandwich.add(fromDate);
        sandwich.add(toDate);

        alignPanel.add(sandwich);

        includePermissions = new CheckBox();
        VerticalPanel vp = new VerticalPanel();
        vp.add(new Label("Include Permissions?"));
        vp.add(includePermissions);

        alignPanel.add(vp);

        this.add(alignPanel);
        this.add(startButton);

    }

    private void initializePopupPanel() {

        loadingPanel = new PopupPanel(false, true);
        HTML h = new HTML("Report wird geladen");
        h.setStyleName("loadingpanel");
        loadingPanel.setWidget(h);
        int left = (int) (Window.getClientWidth() * 0.5);
        int top = (int) (Window.getClientHeight() * 0.5);
        loadingPanel.setPopupPosition(left, top);

    }

    protected void getThaReport() {

        ReportGeneratorAsync rg = ClientsideSettings.getReportGenerator();

        String type = whatKindOfReport.getValue(whatKindOfReport.getSelectedIndex());
        String userEmail = whichUser.getValue();
        if (Objects.equals(userEmail, "")) {
            userEmail = "none";
        }

        Map<String, Date> timespan = new HashMap<>();

        if (fromDate.getValue() != null) {
            timespan.put("from", new Date(fromDate.getValue().getTime()));
        }

        if (toDate.getValue() != null) {
            timespan.put("to", new Date(toDate.getValue().getTime()));
        }

        boolean includePerms = includePermissions.getValue();

        rg.createCustomReport(type, userEmail, timespan, includePerms, new AsyncCallback<CustomReport>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log(caught.toString());
                Notificator.getNotificator().showError("Report cannot be created");
                loadingPanel.hide();
            }

            @Override
            public void onSuccess(CustomReport result) {

                loadingPanel.hide();

                GWT.log(result.toString());
                String s = new HTMLReportWriter().customReport2HTML(result);
                HTML h = new HTML(s);
                ScrollPanel sp = new ScrollPanel(h);

                sp.setSize("100%", "100%");

                ApplicationPanel.getApplicationPanel().replaceContentWith(sp);
            }

        });

    }

    private class AllMailCallback implements AsyncCallback<Vector<String>> {

        @Override
        public void onFailure(Throwable caught) {

            GWT.log(caught.toString());

        }

        @Override
        public void onSuccess(Vector<String> result) {

            oracle.addAll(result);
            Notificator.getNotificator().showSuccess("Emails geholt");

        }

    }

}
