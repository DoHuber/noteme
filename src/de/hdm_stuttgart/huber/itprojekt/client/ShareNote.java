package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionServiceAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;

public class ShareNote extends BasicView{
	private EditorAsync editorVerwaltung  = ClientsideSettings.getEditorVerwaltung();
	private PermissionServiceAsync permissionVerwaltung = ClientsideSettings.getPermissionVerwaltung();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox user = new TextBox();
	private TextBox object = new TextBox();
	private ListBox lb = new ListBox();
	private Button confirmButton = new Button("Confirm");
	private Label usert = new Label("User");
	private Label objectt = new Label("Object");
	private Label level = new Label("Level");
	private Note note = null;
	private String userEmail = null;
	private String levelP = null;
	private Level l = null;
  
	public ShareNote(){
		
	}
	public ShareNote(Note note ){
		this.note = note;
		
	}
	@Override
	public String getHeadlineText() {
	
		return null;
	}

	@Override
	public String getSubHeadlineText() {

		return null;
	}

	@Override
	public void run() {
		
		lb.addItem("Read");
		lb.addItem("Edit");
		lb.addItem("Delete");
		object.setText(note.getTitle());
		lb.setVisibleItemCount(1);
		vPanel.add(usert);
		vPanel.add(user);
		vPanel.add(objectt);
		vPanel.add(object);
		vPanel.add(level);
		vPanel.add(lb);
		confirmButton.addClickHandler(new ConfirmClickHandler());
		vPanel.add(confirmButton);
		
		RootPanel.get("main").add(vPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		
		
	}
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
	
		userEmail = user.getValue();
		levelP = lb.getSelectedItemText();
		switch (levelP){
		case "Read": l = Level.READ;
		break;
		case "Edit": l = Level.EDIT;
		break;
		case "Delete": l = Level.DELETE;
		break;
		
		}
		
		GWT.log("Selected Level:" + l);
		
		permissionVerwaltung.shareWith(userEmail, note, l, new PermissionCallback());	
		}
		
	}
	
	private class PermissionCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
		
	}


}
