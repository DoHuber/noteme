package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.ListItemWidget;
import de.hdm_stuttgart.huber.itprojekt.client.gui.UnorderedListWidget;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotebooksR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNotesR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllPermissionsR;
import de.hdm_stuttgart.huber.itprojekt.shared.report.HTMLReportWriter;
import de.hdm_stuttgart.huber.itprojekt.shared.report.SimpleReport;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Anzeigen eines Reports zum
 * Kunden mit der Kundennummer 1. Demonstration der Nutzung des Report
 * Generators.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * {@link CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies
 * @version 1.0
 * 
 */
public class ShowReportDemo extends MenuView {

	ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

	protected void onLoad() {

		FlowPanel menu = new FlowPanel();
		FlowPanel pureMenu = new FlowPanel();
		UnorderedListWidget menuList = new UnorderedListWidget();

		// Home "Button"
		Anchor home = new Anchor("Home", GWT.getHostPageBaseURL() + "IT_Projekt.html");
		home.setStyleName("pure-menu-heading");
		home.getElement().getStyle().setColor("#ffffff");
		
		//Anchor showUserNotebooks = new Anchor("UserNotebooks");
		Anchor showAllNotebooks = new Anchor("AllNotebooks");
		//Anchor showUserNotes = new Anchor("UserNotes");
		Anchor showAllNotes = new Anchor("AllNotes");
		//Anchor showUserPermissions = new Anchor("UserPermissions");
		Anchor showAllPermissions = new Anchor("AllPermissions");

//		showUserNotebooks.setStyleName("pure-menu-link");
//		menuList.add(new ListItemWidget(showUserNotebooks));

		showAllNotebooks.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllNotebooks));

//		showUserNotes.setStyleName("pure-menu-link");
//		menuList.add(new ListItemWidget(showUserNotes));

		showAllNotes.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllNotes));

//		showUserPermissions.setStyleName("pure-menu-link");
//		menuList.add(new ListItemWidget(showUserPermissions));

		showAllPermissions.setStyleName("pure-menu-link");
		menuList.add(new ListItemWidget(showAllPermissions));

		pureMenu.add(home);
		pureMenu.add(menuList);
		menu.add(pureMenu);
		RootPanel.get("menu").add(menu);

//		showUserNotebooks.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				reportGenerator.createAllUserNotebooksR(new GenericReportCallback<AllUserNotebooksR>());
//			}
//		});

		showAllNotebooks.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reportGenerator.createAllNotebooksR(new GenericReportCallback<AllNotebooksR>());
			}

		});
		
//		showUserNotes.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				reportGenerator.createAllUserNotesR(new GenericReportCallback<AllUserNotesR>());
//
//			}
//
//		});
		
		showAllNotes.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reportGenerator.createAllNotesR(new GenericReportCallback<AllNotesR>());

			}

		});
		
//		showUserPermissions.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				reportGenerator.createAllUserPermissionsR(new GenericReportCallback<AllUserPermissionsR>());
//				;
//
//			}
//
//		});
		
		showAllPermissions.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reportGenerator.createAllPermissionsR(new GenericReportCallback<AllPermissionsR>());

			}

		});

	}

	private void printSimpleReport(SimpleReport r) {

		HTMLReportWriter writer = new HTMLReportWriter();
		String html = writer.simpleReport2HTML(r);
		HTML htmlToDisplay = new HTML(html);

		RootPanel.get("main").clear();
		RootPanel.get("main").add(htmlToDisplay);

	}

	private class GenericReportCallback<T> implements AsyncCallback<T> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(T result) {

			if (result instanceof SimpleReport) {
				SimpleReport r = (SimpleReport) result;
				printSimpleReport(r);
			}
		}
	}

}