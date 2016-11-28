package de.hdm_stuttgart.huber.itprojekt.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.client.ShowAllNotes;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Die Klasse NotebookTable wird alle Notizbücher in einer Tabelle Darstellen
 * 
 * @author Nikita Nalivayko
 *
 */

public class NotebookTable {

	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private FlowPanel fPanel = new FlowPanel();
	private NoteBook selected =null;
	private Vector<NoteBook> notebooks;
	DataGrid<NoteBook> table = new DataGrid<NoteBook>();

	public NotebookTable(Vector<NoteBook> list) {
		this.notebooks = list;
	}

	public Vector<NoteBook> getNotebooks() {
		return notebooks;
	}

	public void setNotebooks(Vector<NoteBook> notebooks) {
		this.notebooks = notebooks;
	}

	public DataGrid<NoteBook> getTable() {
		return table;
	}

	public void setTable(DataGrid<NoteBook> table) {
		this.table = table;
	}

	public FlowPanel start() {
		TextColumn<NoteBook> title = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook nBook) {
				// TODO Auto-generated method stub
				return nBook.getTitle();
			}
		};
		table.addColumn(title, "Title");

		TextColumn<NoteBook> subtitle = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook nBook) {
				// TODO Auto-generated method stub
				return nBook.getSubtitle();
			}
		};
		table.addColumn(subtitle, "Subtitle");

		TextColumn<NoteBook> creationDate = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook nBook) {
				// !!!! Könnte Fehler verursachen
				return nBook.getCreationDate().toString();
			}
		};
		table.addColumn(creationDate, "Creation Date");

		TextColumn<NoteBook> modificationDate = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook nBook) {
				// TODO Auto-generated method stub
				return nBook.getModificationDate().toString();
			}
		};
		table.addColumn(modificationDate, "Modification Date");

		table.setRowCount(notebooks.size(), false);
		
		table.setVisibleRange(0, notebooks.size());
		table.setRowData(0, notebooks);
		LayoutPanel panel = new LayoutPanel();
		panel.setSize("50em", "40em");
		panel.add(table);
		fPanel.add(panel);

		return fPanel;
	}
	/**
	 * Notizbuch wird angeklickt. Übersicht der Notizen in deisem Notizbuch wird gezeigt 
	 */
	public void addClickNotebook(){
		final SingleSelectionModel<NoteBook> selection = new SingleSelectionModel<NoteBook>();
		table.setSelectionModel(selection);
		selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
	}
	private class SelectionChangeHandler implements Handler{
		private final SingleSelectionModel<NoteBook> selection;
		private SelectionChangeHandler(SingleSelectionModel<NoteBook> selection){
			this.selection=selection;
		}

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			selected=selection.getSelectedObject();
			ShowAllNotes sN = new ShowAllNotes(selected);
			RootPanel.get().clear();
			RootPanel.get().add(sN);
			
			
		}
	
	}
}
