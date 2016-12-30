package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;


import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class ShowNotebook extends BasicView{
	
	/**
	 * @author Erdmann, Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben, 
	 */
	private HorizontalPanel vp = new HorizontalPanel();
	private Button deleteBtn = new Button("Delete");
	private Button editBtn = new Button("Update");
	private Button releseBtn = new Button("Release");

	private Button createBtn = new Button ("Create Note");
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	NoteBook nb = null;
	private TextBox title = new TextBox();
	private TextBox subtitle = new TextBox();
	AllNotesCallback callback = new AllNotesCallback();
	
	
	private Vector<Note> notes = new Vector<Note>();

	public ShowNotebook(NoteBook selected) {
	this.nb =selected;
	}

	@Override
	public String getHeadlineText() {

	return "Notizbuch: "+ nb.getTitle();
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		//return "Subtitle: "	+ nb.getSubtitle();
		return null;
	}

	@Override
	public void run() {

		//RootPanel.get("table").getElement().getStyle().setMarginBottom(600,Unit.PX);

		FlowPanel contentPanel = new FlowPanel();
		vp.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		vp.add(editBtn);
		editBtn.addClickHandler(new UpdateClickHandler());
		vp.add(releseBtn);
		releseBtn.addClickHandler(new ShareNotebookClickHndler());
		vp.add(createBtn);
		createBtn.addClickHandler(new CreateNoteClickHandler());
		nb.getId();
		title.setText(nb.getTitle());
		subtitle.setText(nb.getSubtitle());
		//editorVerwaltung.getAllNotes(callback);
		editorVerwaltung.getAllFrom(nb, callback);
//	    NoteTable nt = new NoteTable(notes);
//	    nt.addClickNote();

		contentPanel.add(vp);
		contentPanel.add(title);
		contentPanel.add(subtitle);
		//contentPanel.add(nt.start());

		RootPanel.get("main").add(contentPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
	}
	private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
	    @Override
	    public void onSuccess(Vector<Note> result) {
	      addNotesToTable(result);
	    }

	    @Override
	    public void onFailure(Throwable caught) {}

		
	  }

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		if(	Window.confirm("Möchten Sie das Notizbuch "+ nb.getTitle()+ " wirklich löschen?")){
			editorVerwaltung.deleteNoteBook(nb, new DeleteCallback());
		}
		MenuView navigation = new MenuView();
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(navigation);

		ShowAllNotebooks san =  new ShowAllNotebooks();
		RootPanel.get("main").clear();
		RootPanel.get("main").add(san);


		}}




	public void addNotesToTable(Vector<Note> result) {
	notes = result;
	NoteTable nt = new NoteTable(notes);
	nt.addClickNote();
	// RootPanel.get("main").clear();
	RootPanel.get("tableNotebook").add(nt.start());
	//RootPanel.get("table").clear();
	//RootPanel.get("table").add(nt.start());
}


	private class DeleteCallback implements AsyncCallback<Void>{


		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			vp.add(new Label(caught.toString()));

		}

		@Override
		public void onSuccess(Void result) {


		}
	}
	private class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		if(Window.confirm("Möchten Sie die Änderungen speichern?")){

		}
		nb.setTitle(title.getText());
		nb.setSubtitle(subtitle.getText());
		editorVerwaltung.saveNoteBook(nb, new UpdateCallback());

		}

	}
	private class UpdateCallback implements AsyncCallback<NoteBook>{

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			vp.add(new Label(caught.toString()));

		}

		@Override
		public void onSuccess(NoteBook result) {
		Window.alert("Saved");

		}


	}
	private class CreateNoteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			CreateNote cN = new CreateNote(nb);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(cN);

		}
}
	private class ShareNotebookClickHndler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			ShareNotebook sNb = new ShareNotebook(nb);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(sNb);

		}

	}
}


