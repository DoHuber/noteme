package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		home.setStyleName("pure-menu-heading");
		home.getElement().getStyle().setColor("#ffffff");
		
		//Weitere Button Definitions
		Anchor showNotes = new Anchor("Notes");
		Anchor createNote = new Anchor("New Note");
		Anchor showNotebooks = new Anchor("Notebooks");
		Anchor createNotebook = new Anchor("New Notebook");
		Anchor showPermission = new Anchor("Permissions");
		Anchor reportAnchor = new Anchor("Report");
		
		logoutAnchor = new Anchor("Log out");
		logoutAnchor.setHref(logOutUrl);
		
		showNotes.getElement().getStyle().setColor("#660033");
		showNotebooks.getElement().getStyle().setColor("#660033");
		createNotebook.getElement().getStyle().setColor("#660033");
		createNote.getElement().getStyle().setColor("#660033");
		showPermission.getElement().getStyle().setColor("#660033");
		reportAnchor.getElement().getStyle().setColor("#660033");
		logoutAnchor.getElement().getStyle().setColor("#660033");

		//Test
		//Anchor hello = new Anchor("Say Hello");
		
		//Den Buttons werden Widgets hinzugef�gt
		showNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotes));
		
		createNote.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(createNote));
		
		showNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotebooks));
		
		createNotebook.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(createNotebook));
		
		showPermission.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showPermission));
			
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
		createNote.addClickHandler(new CreateNoteHandler());
		showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
		createNotebook.addClickHandler(new CreateNotebookHandler());
		showPermission.addClickHandler(new ShowPermissionHandler());
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
			new ShowAllNotes().getHeadlineText();
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
			new ShowAllNotes().getHeadlineText();
		}
	}
	
	private class ShowPermissionHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//			MenuView mView = new MenuView();
//			RootPanel.get("menu").clear();
//			RootPanel.get().add(mView);

			ShowPermission san = new ShowPermission();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			new ShowAllNotes().getHeadlineText();
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



