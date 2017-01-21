package de.hdm_stuttgart.huber.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm_stuttgart.huber.itprojekt.client.ApplicationPanel;
import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.client.ShowNote;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

/**
 * Die Klasse NoteTable wird alle Notizen eines bestimmten Notizbuchs in einer
 * Tabelle darstellen
 * 
 * @author Nikita Nalivayko
 * 
 *
 */

public class NoteTable extends FlowPanel {

	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

	/**
	 * Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
	 */
	private Note selected = null;
	private Vector<Note> notes;
	DataGrid<Note> table = new DataGrid<Note>();

	public NoteTable(Vector<Note> list) {
		this.notes = list;
	}

	public NoteTable() {
		
	}

	public Vector<Note> getNotes() {
		return notes;
	}

	public void setNotes(Vector<Note> notes) {
		this.notes = notes;
	}

	public DataGrid<Note> getTable() {
		return table;
	}

	public void setTable(DataGrid<Note> table) {
		this.table = table;
	}
	
	

	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		TextColumn<Note> title = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {

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
				// RICHITGGG Veursacht auch Fehler
				return note.getCreationDate().toString();
			}
		};
		table.addColumn(creationDate, "Creation Date");

		TextColumn<Note> modificationDate = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {
				
				return note.getModificationDate().toString();
				
			}
		};
		table.addColumn(modificationDate, "Modification Date");

		TextColumn<Note> dueDate = new TextColumn<Note>() {

			@Override
			public String getValue(Note note) {

				String resultValue = "Keines gesetzt";
				if (!(note.getDueDate() == null)) {
					resultValue = note.getDueDate().toString();
				}

				return resultValue;
			}
		};
		table.addColumn(dueDate, "Due Date");
			
		table.setRowCount(notes.size(), false);
		table.setWidth("80%");
		table.setVisibleRange(0, notes.size());
		table.setRowData(0, notes);
		
		LayoutPanel panel = new LayoutPanel();
		
		panel.setSize("60em", "40em");
		panel.add(table);
				
		this.add(panel);
	}


	/**
	 * Eine Angeklickte Notiz wird angezeigt
	 */

	public void addClickNote() {
		final SingleSelectionModel<Note> selection = new SingleSelectionModel<Note>();
		table.setSelectionModel(selection);
		selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
	}

	private class SelectionChangeHandler implements Handler {
		private final SingleSelectionModel<Note> selection;

		private SelectionChangeHandler(SingleSelectionModel<Note> selection) {
			this.selection = selection;
		}

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			
			selected = selection.getSelectedObject();
			ShowNote sn = new ShowNote(selected);
			ApplicationPanel.getApplicationPanel().replaceContentWith(sn);
		
		}
	}

}
