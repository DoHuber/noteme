package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.Report.ReportFilter;
import de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentificationAdmin.ReportCheckAdmin;
import de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentificationAdmin.ReportGenerator;

/**
 * 
 * @author Nikita Nalivayko
 *
 */

/*
 * Die Klasse stellt Navigationsmenu des Systems dar.
 * 
 *
 */

public class MenuView extends VerticalPanel {

	protected void onLoad() {

		
		

		VerticalPanel vPanel = new VerticalPanel();

		
		
		Anchor home = new Anchor("NoteMe", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		Anchor showNotes = new Anchor("Notes ");
		Anchor showNotebooks = new Anchor("Notebooks");
		Anchor createNotebook = new Anchor("New Notebook");
		Anchor createNote = new Anchor("New Note");
		Anchor reportAnchor = new Anchor("Report");
		Anchor logoutAnchor = new Anchor("Logout");
		//Test
		Anchor hello = new Anchor("Say Hello");

		vPanel.add(home);
		vPanel.add(showNotes);
		vPanel.add(showNotebooks);
		vPanel.add(createNotebook);
		vPanel.add(createNote);
		vPanel.add(reportAnchor);
		vPanel.add(logoutAnchor);
		//Test
		vPanel.add(hello);



		RootPanel.get().add(vPanel);
		/*
	       * Die "Buttons"  werden mit dem ClickHandler verbunden. Die "Buttons reagieren aud den Mausklick."
	       *
	    */

	
		showNotes.addClickHandler(new ShowAllNotesHandler());
		showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
		createNotebook.addClickHandler(new CreateNotebookHandler());
		createNote.addClickHandler(new CreateNoteHandler());
		reportAnchor.addClickHandler(new ReportHandler());
		logoutAnchor.addClickHandler(new LogoutHandler());
		//Test
		hello.addClickHandler(new testClickHandler());
	}
	
	/*Einfache ClickHandler werden implementiert 
	 * 
	 */
	private class ShowAllNotesHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);

			ShowAllNotes san = new ShowAllNotes(null);
			RootPanel.get().add(san);
		}
	}

	private class ShowAllNotebooksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);

			ShowAllNotebooks san = new ShowAllNotebooks();
			RootPanel.get().add(san);
		}
	}
	private class CreateNotebookHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);

			CreateNotebook cN = new CreateNotebook();
			RootPanel.get().add(cN);
		}
	}
	private class CreateNoteHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);

			CreateNote cN = new CreateNote();
			RootPanel.get().add(cN);
		}
	}
	private class ReportHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ReportCheckAdmin rpChA = new ReportCheckAdmin();
			RootPanel.get().clear();
			RootPanel.get().add(rpChA);
			

	
		}
	}
	private class LogoutHandler implements ClickHandler {
	

		@Override
		public void onClick(ClickEvent event) {
			Label lb = new Label("Ich melde mich ab");
			RootPanel.get().clear();
			RootPanel.get().add(lb);
			
		
		}
	}
	//Später löschen 
	private class testClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);
			
			HelloWorld hw = new HelloWorld();
			
			RootPanel.get().add(hw);
			
		}
		
	}


}
