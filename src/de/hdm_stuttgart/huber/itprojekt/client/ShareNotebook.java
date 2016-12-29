package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class ShareNotebook extends BasicView {
	
	private EditorAsync editorVerwaltung  = ClientsideSettings.getEditorVerwaltung();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox user = new TextBox();
	private TextBox object = new TextBox();
	private ListBox lb = new ListBox();
	private Button confirmButton = new Button("Confirm");
	private Label usert = new Label("User");
	private Label objectt = new Label("Object");
	private Label level = new Label("Level");
	private NoteBook nb = null;
	
	public ShareNotebook(){
		
	}
	public ShareNotebook(NoteBook nb ){
		this.nb=nb;
		
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		lb.addItem("Level 1");
		lb.addItem("Level 2");
		lb.addItem("Level 3");
		object.setText(nb.getTitle());
		lb.setVisibleItemCount(1);
		vPanel.add(usert);
		vPanel.add(user);
		vPanel.add(objectt);
		vPanel.add(object);
		vPanel.add(level);
		vPanel.add(lb);
		vPanel.add(confirmButton);
		RootPanel.get("main").add(vPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();

	}

}
