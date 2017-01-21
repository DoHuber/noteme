package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
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

	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	UserCallback uc = new UserCallback();
	UserInfo ui = null;

	protected void onLoad() {

		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setStyleName("headerpanel");

		editorVerwaltung.getCurrentUser(uc);

		Anchor home = new Anchor("Home", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		home.setStyleName("pure-menu-heading");
		home.getElement().getStyle().setColor("#ffffff");
		
		this.add(home);

		Anchor showNotes = new Anchor("Notes");
		Anchor showNotebooks = new Anchor("Notebooks");
		Anchor showPermission = new Anchor("Shared stuff");

		showNotes.setStyleName("pure-menu-link");
		this.add(new ListItemWidget(showNotes));

		showNotebooks.setStyleName("pure-menu-link");
		this.add(new ListItemWidget(showNotebooks));

		showPermission.setStyleName("pure-menu-link");
		this.add(new ListItemWidget(showPermission));

		showNotes.addClickHandler(new ShowAllNotesHandler());
		showNotebooks.addClickHandler(new ShowAllNotebooksHandler());
		showPermission.addClickHandler(new ShowPermissionHandler());

	}

	private class ShowAllNotesHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			ShowAllNotes san = new ShowAllNotes();
			ApplicationPanel.getApplicationPanel().replaceContentWith(san);

		}
	}

	private class ShowAllNotebooksHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			ShowAllNotebooks san = new ShowAllNotebooks();
			ApplicationPanel.getApplicationPanel().replaceContentWith(san);

		}
	}

	private class ShowPermissionHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			ShowAllPermissions sp = new ShowAllPermissions();
			ApplicationPanel.getApplicationPanel().replaceContentWith(sp);

		}
	}

	private class UserCallback implements AsyncCallback<UserInfo> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(UserInfo result) {
			ui = result;
		}
	}

}
