package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;


public class Account extends BasicView {
	
	private VerticalPanel contentPanel = new VerticalPanel();
	private UserInfo ui = null;
	private Button deleteBtn = new Button("Delete");
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	UserCallback uc = new UserCallback();
	
	public Account () {
		
	}
	
	public Account (UserInfo userInfo) {
		this.ui = userInfo;
	}

	@Override
	public String getHeadlineText() {
		return "MY ACCOUNT";
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "User: " + ui.getFirstName() + ui.getSurName();
		
	}
	
	public String accountInformation() {
		return 	"Nickname: " + ui.getNickname()
				+ "E-Mail: " + ui.getEmailAddress();
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//contentPanel.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		
		editorVerwaltung.getCurrentUser(uc);
		
		RootPanel.get("main").add(deleteBtn);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		
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
	
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		if(	Window.confirm("Möchten Sie Ihren Account "+ ui.getNickname()+ " wirklich löschen?")){
			editorVerwaltung.deleteUserInfo(ui, new DeleteCallback());
		}
		MenuView navigation = new MenuView();
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(navigation);

		Account san =  new Account();
		RootPanel.get("main").clear();
		RootPanel.get("main").add(san);
			
		}
		
	}
	
	private class DeleteCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			contentPanel.add(new Label(caught.toString()));
			
		}

		@Override
		public void onSuccess(Void result) {
			
		}
		
	}

}
