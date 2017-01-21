package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class Account extends BasicView {

	private VerticalPanel contentPanel = new VerticalPanel();
	private UserInfo loggedInUser = null;
	private Button deleteButton = new Button("Delete my account");
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	UserCallback userCallback = new UserCallback();
	private static String logOutUrl;
	private VerticalPanel vp = new VerticalPanel();
	private TextBox name = new TextBox();
	private TextBox surname  = new TextBox();
	private TextBox	email = new TextBox();

	public Account() {

	}

	public Account(UserInfo userInfo) {
		this.loggedInUser = userInfo;
	}

	@Override
	public String getHeadlineText() {
		return "MY ACCOUNT";
		// TODO Auto-generated method stub

	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Nickname: " + loggedInUser.getNickname();

	}



	@Override
	public void run() {
		name.setText(loggedInUser.getFirstName());
		surname.setText(loggedInUser.getSurName());
		email.setText(loggedInUser.getEmailAddress());
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp.setWidth("100%");
		vp.add(name);
		vp.add(surname);
		vp.add(email);
		vp.add(deleteButton);
		deleteButton.addClickHandler(new DeleteClickHandler());

		editorVerwaltung.getCurrentUser(userCallback);
	
		/**
		RootPanel.get("main").add(vp);
		RootPanel.get("main").add(deleteButton);
		
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		**/

	}

	private class UserCallback implements AsyncCallback<UserInfo> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(UserInfo result) {

			loggedInUser = result;

		}

	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Möchten Sie Ihren Account " + loggedInUser.getNickname() + " wirklich löschen?")) {
				
				logOutUrl = loggedInUser.getLogoutUrl();
				editorVerwaltung.deleteUserInfo(loggedInUser, new DeleteCallback());
				
			}

		}
	}

	public static void setLogOutUrl(String logOutUrl) {
		Account.logOutUrl = logOutUrl;
	}

	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			contentPanel.add(new Label(caught.toString()));

		}

		@Override
		public void onSuccess(Void result) {

			// Und raus damit
			Window.Location.replace(logOutUrl);
			
		}

	}

}
