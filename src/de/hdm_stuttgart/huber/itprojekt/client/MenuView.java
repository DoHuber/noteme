package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
import de.hdm_stuttgart.huber.itprojekt.client.gui.UnorderedListWidget;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

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
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	UserCallback uc = new UserCallback();
	UserInfo ui = null;

	protected void onLoad() {

		// MenuPanel
		FlowPanel menu = new FlowPanel();
		FlowPanel pureMenu = new FlowPanel();
		UnorderedListWidget menuList = new UnorderedListWidget();

		editorVerwaltung.getCurrentUser(uc);
		// VerticalPanel vPanel = new VerticalPanel();

		RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#ffffff");

		// Home "Button"
		Anchor home = new Anchor("Home", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		home.setStyleName("pure-menu-heading");
		home.getElement().getStyle().setColor("#ffffff");

		// Weitere Button Definitions
		Anchor showNotes = new Anchor("Notes");
		//Anchor createNote = new Anchor("New Note");
		Anchor showNotebooks = new Anchor("Notebooks");
		//Anchor createNotebook = new Anchor("New Notebook");
		Anchor showPermission = new Anchor("Shared");
		Anchor account = new Anchor("Account");
		Anchor reportAnchor = new Anchor("ReportGenerator");
		Anchor embedAnchor = new Anchor("Embed");

		logoutAnchor = new Anchor("Log out");
		logoutAnchor.setHref(logOutUrl);

		showNotes.getElement().getStyle().setColor("#660033");
		showNotebooks.getElement().getStyle().setColor("#660033");
		//createNotebook.getElement().getStyle().setColor("#660033");
		//createNote.getElement().getStyle().setColor("#660033");
		showPermission.getElement().getStyle().setColor("#660033");
		account.getElement().getStyle().setColor("#660033");
		reportAnchor.getElement().getStyle().setColor("#660033");
		logoutAnchor.getElement().getStyle().setColor("#660033");
		embedAnchor.getElement().getStyle().setColor("#660033");

		// Test
		// Anchor hello = new Anchor("Say Hello");

		// Den Buttons werden Widgets hinzugef�gt
		showNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotes));

//		createNote.setStyleName("pure-menu-link");
//		menuList.add(new ListItemWidget(createNote));

		showNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showNotebooks));

//		createNotebook.setStyleName("pure-menu-link");
//		menuList.add(new ListItemWidget(createNotebook));

		showPermission.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showPermission));

		account.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(account));

		reportAnchor.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(reportAnchor));
		
		embedAnchor.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(embedAnchor));

		logoutAnchor.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(logoutAnchor));

		pureMenu.add(home);
		pureMenu.add(menuList);
		menu.add(pureMenu);

		RootPanel.get("menu").add(menu);
		/**
		 * Die "Buttons" werden mit dem ClickHandler verbunden. Die "Buttons
		 * reagieren auf den Mausklick."
		 *
		 */

		showNotes.addClickHandler(new ShowAllNotesHandler());
		//createNote.addClickHandler(new CreateNoteHandler());
		showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
		//createNotebook.addClickHandler(new CreateNotebookHandler());
		showPermission.addClickHandler(new ShowPermissionHandler());
		account.addClickHandler(new AccountHandler());
		reportAnchor.addClickHandler(new ReportHandler());
		logoutAnchor.addClickHandler(new LogoutHandler());
		
		embedAnchor.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new EmbedCode());
				
			}
		
		});



	}

	/*
	 * Einfache ClickHandler werden implementiert
	 * 
	 */
	private class ShowAllNotesHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// MenuView mView = new MenuView();
			// RootPanel.get("menu").clear();
			// RootPanel.get("menu").add(mView);

			ShowAllNotes san = new ShowAllNotes();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			new ShowAllNotes().getHeadlineText();
		}
	}

	private class ShowAllNotebooksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// MenuView mView = new MenuView();
			// RootPanel.get("menu").clear();
			// RootPanel.get().add(mView);

			ShowAllNotebooks san = new ShowAllNotebooks();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			new ShowAllNotes().getHeadlineText();
		}
	}

	private class ShowPermissionHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// MenuView mView = new MenuView();
			// RootPanel.get("menu").clear();
			// RootPanel.get().add(mView);

			ShowPermission san = new ShowPermission();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			new ShowAllNotes().getHeadlineText();
		}
	}

//	private class CreateNotebookHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			MenuView mView = new MenuView();
//			RootPanel.get("menu").clear();
//			RootPanel.get("menu").add(mView);
//
//			CreateNotebook cN = new CreateNotebook();
//			RootPanel.get("main").clear();
//			RootPanel.get("main").add(cN);
//		}
//	}

	private class AccountHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Account san = new Account(ui);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
			new Account().getHeadlineText();
		}
	}

	private class UserCallback implements AsyncCallback<UserInfo>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(UserInfo result) {
			ui = result;
		}
	}

	private class ReportHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.replace(GWT.getHostPageBaseURL() + "Report.html");

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

	protected void run() {
	}

	protected void append(String text) {
		HTML content = new HTML(text);
		content.setStylePrimaryName("bankproject-simpletext");
		this.add(content);
	}
}
