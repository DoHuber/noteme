package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentificationAdmin.ReportCheckAdmin;
import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
import de.hdm_stuttgart.huber.itprojekt.client.gui.UnorderedListWidget;

/**
 * 
 * @author Nikita Nalivayko
 *
 */

/*
 * Die Klasse stellt das Navigationsmenu des Systems dar.
 * 
 *
 */

public class MenuView extends VerticalPanel {
	
	private static String logOutUrl;
	private Anchor logoutAnchor;

	protected void onLoad() {

		//MenuPanel
		FlowPanel menu  = new FlowPanel();
		FlowPanel pureMenu  = new FlowPanel();
		UnorderedListWidget menuList = new UnorderedListWidget();

	//	VerticalPanel vPanel = new VerticalPanel();

		RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#ffffff");
		
		//Home "Button"
		Anchor home = new Anchor("Home", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		//RootPanel.get("home").getElement().getStyle().setColor("#660033");
		//Weitere Button Definitions
		Anchor showNotes = new Anchor("Notes ");
		Anchor showNotebooks = new Anchor("Notebooks");
		Anchor createNotebook = new Anchor("New Notebook");
		Anchor createNote = new Anchor("New Note");
		Anchor reportAnchor = new Anchor("Report");
		
		logoutAnchor = new Anchor("Log out");
		logoutAnchor.setHref(logOutUrl);

		//Test
		//Anchor hello = new Anchor("Say Hello");
		
		//Den Buttons werden Widgets hinzugef�gt
		showNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotes));
		
		showNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotebooks));
		
		createNotebook.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(createNotebook));
		
		createNote.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(createNote));
		
		reportAnchor.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(reportAnchor));
		
		logoutAnchor.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(logoutAnchor));


			pureMenu.add(home);
			pureMenu.add(menuList);
			menu.add(pureMenu);

			RootPanel.get("menu").add(menu);
	      /**
	       * Die "Buttons"  werden mit dem ClickHandler verbunden. Die "Buttons reagieren auf den Mausklick."
	       *
	    */

		showNotes.addClickHandler(new ShowAllNotesHandler());
		showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
		createNotebook.addClickHandler(new CreateNotebookHandler());
		createNote.addClickHandler(new CreateNoteHandler());
		reportAnchor.addClickHandler(new ReportHandler());
		logoutAnchor.addClickHandler(new LogoutHandler());
		

	}
	
	/*Einfache ClickHandler werden implementiert 
	 * 
	 */
	private class ShowAllNotesHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//			MenuView mView = new MenuView();
//			RootPanel.get("menu").clear();
//			RootPanel.get("menu").add(mView);

			ShowAllNotes san = new ShowAllNotes();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			
		}
	}

	private class ShowAllNotebooksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//			MenuView mView = new MenuView();
//			RootPanel.get("menu").clear();
//			RootPanel.get().add(mView);

			ShowAllNotebooks san = new ShowAllNotebooks();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
		}
	}
	private class CreateNotebookHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			CreateNotebook cN = new CreateNotebook();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(cN);
		}
	}
	private class CreateNoteHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			CreateNote cN = new CreateNote();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(cN);
		}
	}
	private class ReportHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ReportCheckAdmin rpChA = new ReportCheckAdmin();
			RootPanel.get("menu").clear();
			RootPanel.get().add(rpChA);
			

	
		}
	}
	private class LogoutHandler implements ClickHandler {
	

		@Override
		public void onClick(ClickEvent event) {
			
			Label lb = new Label("Ich melde mich ab");
			RootPanel.get("menu").clear();
			RootPanel.get().add(lb);		
		
		}

		
	}
	public static void setLogOutUrl(String logOutUrl) {
		MenuView.logOutUrl = logOutUrl;
	}

	
}



