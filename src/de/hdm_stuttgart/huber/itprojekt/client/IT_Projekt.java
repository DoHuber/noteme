package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Arrays;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IT_Projekt implements EntryPoint {
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private UserInfo userInfo = null;

	private Audio bootSound;
	private Audio bootAdminSound;
	private VerticalPanel user = new VerticalPanel();
	private Label name = new Label("First Name");
	private TextBox nameBox = new TextBox();
	private Label name2 = new Label("Last Name");
	private TextBox nameBox2 = new TextBox();
	private Button btn = new Button("Save");

	ApplicationPanel applicationPanel;
	Button accountButton = new Button(IconConstants.ICON_ACCOUNT_CIRCLE);

	public void onModuleLoad() {

		setUpUncaughtExceptionHandler();

		applicationPanel = ApplicationPanel.getApplicationPanel();
		applicationPanel.setStyleName("dockpanel");

		setUpHeaderPanel();
		setUpFooter();

		RootLayoutPanel.get().add(applicationPanel);

		GWT.log("Servus i bims");
		SharedServicesAsync loginService = GWT.create(SharedServices.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {

			public void onFailure(Throwable e) {
				GWT.log(e.toString());
			}

			public void onSuccess(UserInfo result) {

				userInfo = result;

				if (userInfo.isLoggedIn()) {

					initializeAudio();

					if (!userInfo.isAdmin()) {

						IT_Projekt.this.bootSound.play();

					} else {

						IT_Projekt.this.bootAdminSound.play();

					}

					if (userInfo.getFirstName() == null && userInfo.getSurName() == null) {
						createUser();
					}

					else
						checkIfNewNote();
				} else {

					loadLogin();
				}

			}

		});

	}

	private void setUpFooter() {

		HorizontalPanel footerPanel = new HorizontalPanel();
		footerPanel.add(new Label("Gruppe 7 IT-Projekt: Hochschule der Medien"));
		Anchor a = new Anchor("Report Generator");
		a.setHref(GWT.getHostPageBaseURL() + "Report.html");
		footerPanel.add(a);

		applicationPanel.setFooter(footerPanel);

	}

	private void setUpHeaderPanel() {

		HorizontalPanel headerPanel = new HorizontalPanel();

		headerPanel.setStyleName("headerpanel");
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		headerPanel.add(new HTML("<img width=\"33%\" src=\"LeftUpperSprite.jpg\" alt=\"Fehler\">"));

		Label l = new Label("NoteMe");
		l.setStyleName("headerlabel");
		headerPanel.add(l);

		accountButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				openAccountPanel();
			}

		});

		headerPanel.add(accountButton);

		headerPanel.setCellWidth(headerPanel.getWidget(0), "33%");
		headerPanel.setCellWidth(headerPanel.getWidget(1), "33%");
		headerPanel.setCellWidth(headerPanel.getWidget(2), "33%");

		applicationPanel.setHeader(headerPanel);
	}

	private void setUpUncaughtExceptionHandler() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				GWT.log(e.getMessage());
				GWT.log(Arrays.toString(e.getStackTrace()));
			}
		});
	}

	private void openAccountPanel() {

		AccountPanel ap = new AccountPanel(userInfo);
		ap.setPopupPosition(accountButton.getAbsoluteLeft(), accountButton.getAbsoluteTop());
		ap.show();

	}

	private void checkIfNewNote() {

		editorVerwaltung.getSource(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.toString());
				loadMenu();
				loadDueNotes();
			}

			@Override
			public void onSuccess(String result) {
				if (result == "none") {

					loadMenu();
					loadDueNotes();

				} else {

					CreateNote prefilledCreateForm = new CreateNote(result);
					applicationPanel.replaceContentWith(prefilledCreateForm);

					loadMenu();

				}
			}

		});

	}

	/*
	 * Navigationsmenu wird geladen
	 *
	 */
	private void loadMenu() {

		MenuView navigation = new MenuView();

		applicationPanel.setNavigation(navigation);

	}

	private void loadDueNotes() {

		DueDateFromUser du = new DueDateFromUser(userInfo);
		applicationPanel.setCenterContent(du);

	}

	private void loadLogin() {

		Label l = new Label("You need to sign in in order to access this application.");
		Anchor a = new Anchor("Sign in");
		a.setHref(userInfo.getLoginUrl());

		VerticalPanel vp = new VerticalPanel();
		vp.add(l);
		vp.add(a);

		ApplicationPanel.getApplicationPanel().replaceContentWith(vp);

	}

	public void createUser() {

		user.add(name);
		user.add(nameBox);
		user.add(name2);

		user.add(nameBox2);
		user.add(btn);
		btn.addClickHandler(new SaveClickHandler());

		ApplicationPanel.getApplicationPanel().replaceContentWith(user);

	}

	private void initializeAudio() {

		bootSound = Audio.createIfSupported();
		bootSound.setSrc("style/audiores/startup.mp3");

		bootAdminSound = Audio.createIfSupported();
		bootAdminSound.setSrc("style/audiores/startupadmin.mp3");

	}

	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			userInfo.setFirstName(nameBox.getText());
			userInfo.setSurName(nameBox2.getText());
			editorVerwaltung.saveUser(userInfo, new SaveCallBack());

		}

		private class SaveCallBack implements AsyncCallback<UserInfo> {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(UserInfo result) {

				loadMenu();
				loadDueNotes();
				Notificator.getNotificator().showSuccess("Welcome to NoteMe!");

			}

		}
	}

}
