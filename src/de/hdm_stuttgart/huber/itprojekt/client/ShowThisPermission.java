package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.PermissionServiceAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;

public class ShowThisPermission extends BasicView {


	private VerticalPanel alignPanel = new VerticalPanel();
	private Button deleteBtn = new Button("Delete");
	private Label level = new Label("Level");
	private Label user = new Label("User");
	private Label object = new Label("Object");
	private TextBox levelTextBox = new TextBox();
	private TextBox userTextBox = new TextBox();
	private TextBox objectTextBox = new TextBox();
	
	PermissionServiceAsync permissionVerwaltung = ClientsideSettings.getPermissionVerwaltung();
	private Permission p = null;
	private Note note = null;
	private NoteBook notebook = null;
	
	public ShowThisPermission (){
		
	}
	
	
	public ShowThisPermission(Permission p) {
		this.p = p;
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubHeadlineText() {
		
		return null;
	}

	@Override
	public void run() {
	alignPanel.add(level);
	alignPanel.add(levelTextBox);
	alignPanel.add(user);
	alignPanel.add(userTextBox);
	alignPanel.add(object);
	alignPanel.add(objectTextBox);
	alignPanel.add(deleteBtn);
	deleteBtn.addClickHandler(new DeleteClickHandler());
	
	
	String string = null;
	if(p.getLevelAsInt()==10){
		string="Read";
	}
	else if(p.getLevelAsInt()==20){
		string="Edit";
	}
	else if(p.getLevelAsInt()==20){
		string=	"Delete";
		}
	levelTextBox.setText(string);
	userTextBox.setText(p.getBeneficiary().getNickname());
	
	String noteB = String.valueOf(p.getSharedObject().getType());
	String string2 =null;
	if(noteB == "b"){
		 notebook = (NoteBook) p.getSharedObject();
		 string2= "Notebook: "+ notebook.getTitle();
	}
	else{
		note = (Note) p.getSharedObject();
		 string2= "Note: "+ note.getTitle();
}
	
	objectTextBox.setText(string2);
	
	RootPanel.get("main").add(alignPanel);
	RootPanel.get("table").clear();
	RootPanel.get("tableNotebook").clear();
	
		
	}
	
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		if(	Window.confirm("Do you want to delete the selected permission ")){
			permissionVerwaltung.deletePermission(p, new DeleteCallback());
		}
		MenuView navigation = new MenuView();
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(navigation);	
		
		ShowAllNotes san =  new ShowAllNotes();
		RootPanel.get("main").clear();
		RootPanel.get("main").add(san);
			
		}
	}
	private class DeleteCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			alignPanel.add(new Label(caught.toString()));
			
		}

		@Override
		public void onSuccess(Void result) {
		
			
			
		}
	
}
}
