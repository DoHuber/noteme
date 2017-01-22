package de.hdm_stuttgart.huber.itprojekt.client;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.report.CustomReport;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;

public class FilterAbleReports extends BasicVerticalView {

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
	
	private HorizontalPanel alignPanel;
	private ListBox whatKindOfReport;
	private SuggestBox whichUser;
	private Button startButton;
	private CheckBox includePermissions;
	
	private DateBox fromDate;
	private DateBox toDate;
	
	private final static String[] REPORT_CHOICES = {"notes", "notebooks", "permissions"};
	private MultiWordSuggestOracle oracle;
	
	EditorAsync editor = ClientsideSettings.getEditorVerwaltung();

	@Override
	public void run() {
		
		whatKindOfReport = new ListBox();
		
		for (String element : REPORT_CHOICES) {
			whatKindOfReport.addItem(element);
		}
		
		whatKindOfReport.addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				String currentlySelected = whatKindOfReport.getValue(whatKindOfReport.getSelectedIndex());
				
				if (currentlySelected == "permissions") {
					
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
		
		startButton = new Button("Generate");
		startButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ApplicationPanel.getApplicationPanel().replaceContentWith(new Label("Es wird geladen"));
				getThaReport();
			}
	
		});
		
		alignPanel = new HorizontalPanel();
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
	
	protected void getThaReport() {
		
		ReportGeneratorAsync rg = ClientsideSettings.getReportGenerator();
		
		String type = whatKindOfReport.getValue(whatKindOfReport.getSelectedIndex());
		String userEmail = whichUser.getValue();
		if (userEmail == "") {
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
		
		rg.createCustomReport(type, userEmail, timespan, includePerms, new AsyncCallback<CustomReport>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.toString());
				Notificator.getNotificator().showError("Report cannot be created");
			}

			@Override
			public void onSuccess(CustomReport result) {
				
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
