package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;

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
	
	private final static String[] REPORT_CHOICES = {"notes", "notebooks", "permissions"};
	private MultiWordSuggestOracle oracle;
	
	EditorAsync editor = ClientsideSettings.getEditorVerwaltung();

	@Override
	public void run() {
		
		whatKindOfReport = new ListBox();
		
		for (String element : REPORT_CHOICES) {
			whatKindOfReport.addItem(element);
		}
		
		whatKindOfReport.setVisibleItemCount(REPORT_CHOICES.length);
		
		oracle = new MultiWordSuggestOracle();
		whichUser = new SuggestBox(oracle);
		
		editor.getAllEmails(new AllMailCallback());
		
		startButton = new Button("Generate");
		
		alignPanel = new HorizontalPanel();
		alignPanel.setWidth("60%");
		alignPanel.setVerticalAlignment(ALIGN_MIDDLE);
		
		alignPanel.add(whatKindOfReport);
		alignPanel.add(whichUser);
		alignPanel.add(startButton);
		
		this.add(alignPanel);

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
