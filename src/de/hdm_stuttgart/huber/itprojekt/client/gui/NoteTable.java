package de.hdm_stuttgart.huber.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.client.MenuView;
import de.hdm_stuttgart.huber.itprojekt.client.ShowAllNotebooks;
import de.hdm_stuttgart.huber.itprojekt.client.ShowNote;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
/**
 * Die Klasse NoteTable wird alle Notizen eines bestimmten Notizbuchs in einer Tabelle darstellen 
 * @author Nikita Nalivayko 
 * 
 *
 */

public class NoteTable {

	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private FlowPanel fPanel = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	/**
	 * Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
	 */
	private Button deleteBtn = new Button("Delete");
	private Button editBtn	= new Button("Update");
	private Button releseBtn = new Button("Relese");
	//Wird später gelöscht 
	private Button testBtn = new Button("Test");
	private NoteBook notebook=null;
	private Note selected = null;
	private Vector<Note> notes;
	DataGrid<Note> table = new DataGrid<Note>();

	public NoteTable(Vector<Note> list) {
		this.notes = list;
	}

	public Vector<Note> getNotebooks() {
		return notes;
	}

	public void setNotebooks(Vector<Note> notes) {
		this.notes = notes;
	}

	public DataGrid<Note> getTable() {
		return table;
	}

	public void setTable(DataGrid<Note> table) {
		this.table = table;
	}

	public FlowPanel start() {
		TextColumn<Note> title = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				// TODO Auto-generated method stub
				return note.getTitle();
			}
		};
		table.addColumn(title, "Title");

		TextColumn<Note> subtitle = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				// TODO Auto-generated method stub
				return note.getSubtitle();
			}
		};
		table.addColumn(subtitle, "Subtitle");

		TextColumn<Note> creationDate = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				// !!!! Könnte Fehler verursachen
				return note.getCreationDate().toString();
			}
		};
		table.addColumn(creationDate, "Creation Date");

		TextColumn<Note> modificationDate = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				// TODO Auto-generated method stub
				return note.getModificationDate().toString();
			}
		};
		table.addColumn(modificationDate, "Modification Date");
		
		TextColumn<Note> dueDate = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				// TODO Auto-generated method stub
				return note.getDueDate().toString();
			}
		};
		table.addColumn(dueDate, "Due Date");
		
		table.setRowCount(notes.size(), false);

		table.setVisibleRange(0, notes.size());
		table.setRowData(0, notes);
		LayoutPanel panel = new LayoutPanel();
		panel.setSize("50em", "40em");
		panel.add(table);
		buttonPanel.add(deleteBtn);
		//deleteBtn.addClickHandler(new DeleteClickHandler());
		buttonPanel.add(editBtn);
		buttonPanel.add(releseBtn);
		//Test
		buttonPanel.add(testBtn);
		testBtn.addClickHandler(new TestClickHandler());
		
		fPanel.add(buttonPanel);
		fPanel.add(panel);

		return fPanel;
	}
	private class TestClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mView);

			ShowNote sn = new ShowNote();
			RootPanel.get().add(sn);
			
		}
		
	}
	private class DeleteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			/**
			 * Sicherheitsfunktion. Soll das Notizbuch wirklich gelöscht werden?
			 */
		if (Window.confirm("Wollen Sie das notizbuch löschen?")){
			fPanel.add(new HTML("<p> Das Notizbuch wurde gelöscht</p>"));
		}
			
		}
		
	}
	/**
	 * Eine Angeklickte Notiz wird angezeigt 
	 */

	public void addClickNote() {
		final SingleSelectionModel<Note> selection = new SingleSelectionModel<Note>();
		table.setSelectionModel(selection);
		selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
	}

	private class SelectionChangeHandler implements Handler{
		private final SingleSelectionModel<Note> selection;
		private SelectionChangeHandler(SingleSelectionModel<Note> selection){
			this.selection=selection;
		}

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			selected=selection.getSelectedObject();
			ShowNote sn = new ShowNote();
			
			RootPanel.get().clear();
			RootPanel.get().add(sn);
			
			
		}}

}
