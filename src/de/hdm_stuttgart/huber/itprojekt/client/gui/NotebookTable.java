package de.hdm_stuttgart.huber.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm_stuttgart.huber.itprojekt.client.ApplicationPanel;
import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.client.ShowNotebook;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Die Klasse NotebookTable wird alle Notizbücher in einer Tabelle Darstellen
 * 
 * @author Erdmann, Nalivayko
 *
 */

public class NotebookTable extends FlowPanel {

	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private FlowPanel buttonPanel = new FlowPanel();

	/**
	 * Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
	 */
	private NoteBook selected = null;
	private Vector<NoteBook> noteB;
	DataGrid<NoteBook> table = new DataGrid<NoteBook>();

	public NotebookTable(Vector<NoteBook> list) {
		this.noteB = list;
	}

	public Vector<NoteBook> getNoteBooks() {
		return noteB;
	}

	public void setNoteBooks(Vector<NoteBook> noteB) {
		this.noteB = noteB;
	}

	public DataGrid<NoteBook> getTable() {
		return table;
	}

	public void setTable(DataGrid<NoteBook> table) {
		this.table = table;
	}

	@Override
	public void onLoad() {
		TextColumn<NoteBook> title = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook noteB) {

				return noteB.getTitle();
			}
		};
		table.addColumn(title, "Title");

		TextColumn<NoteBook> subtitle = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook noteB) {
				// TODO Auto-generated method stub
				return noteB.getSubtitle();
			}
		};
		table.addColumn(subtitle, "Subtitle");

		TextColumn<NoteBook> creationDate = new TextColumn<NoteBook>() {

			@Override
			public String getValue(NoteBook noteB) {
				// !!!! Könnte Fehler verursachen
				return noteB.getCreationDate().toString();
			}
		};
		table.addColumn(creationDate, "Creation Date");

		table.setRowCount(noteB.size(), false);
		table.setWidth("80%");
		table.setVisibleRange(0, noteB.size());
		table.setRowData(0, noteB);
		LayoutPanel panel = new LayoutPanel();
		panel.setSize("60em", "40em");
		panel.add(table);

		table.setStyleName("googleTable");

		this.add(buttonPanel);
		this.add(panel);

	}

	@SuppressWarnings("unused")
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			/**
			 * Sicherheitsfunktion. Soll das Notizbuch wirklich gelöscht werden?
			 */
			if (Window.confirm("Wollen Sie das notizbuch löschen?")) {
				NotebookTable.this.add(new HTML("<p> Das Notizbuch wurde gelöscht</p>"));
			}

		}

	}

	/**
	 * Ein angeklicktes Notizbuch wird angezeigt
	 */

	public void addClickNote() {
		final SingleSelectionModel<NoteBook> selection = new SingleSelectionModel<NoteBook>();
		table.setSelectionModel(selection);
		selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
	}

	private class SelectionChangeHandler implements Handler {
		private final SingleSelectionModel<NoteBook> selection;

		private SelectionChangeHandler(SingleSelectionModel<NoteBook> selection) {
			this.selection = selection;
		}

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			
			selected = selection.getSelectedObject();
			ShowNotebook sn = new ShowNotebook(selected);
			ApplicationPanel.getApplicationPanel().replaceContentWith(sn);

		}
	}

}
