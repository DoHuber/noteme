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
import de.hdm_stuttgart.huber.itprojekt.client.ShowThisPermission;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;

/**
 * Die Klasse NoteTable wird alle Notizen eines bestimmten Notizbuchs in einer
 * Tabelle darstellen
 * 
 * @author Nikita Nalivayko
 * 
 *
 */

public class PermissionTable extends FlowPanel {

	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();

	/**
	 * Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
	 */
	private Note note = null;
	private NoteBook notebook = null;
	private Permission selected = null;
	private Vector<Permission> permission;
	DataGrid<Permission> table = new DataGrid<Permission>();

	public PermissionTable(Vector<Permission> list) {
		this.permission = list;
	}

	public Vector<Permission> getPermissions() {
		return permission;
	}

	public void setNotes(Vector<Permission> permission) {
		this.permission = permission;
	}

	public DataGrid<Permission> getTable() {
		return table;
	}

	public void setTable(DataGrid<Permission> table) {
		this.table = table;
	}

	@Override
	public void onLoad() {

		TextColumn<Permission> level = new TextColumn<Permission>() {

			@Override
			public String getValue(Permission permission) {
				String string = null;
				if (permission.getLevelAsInt() == 10) {
					string = "Read";
				} else if (permission.getLevelAsInt() == 20) {
					string = "Edit";
				} else if (permission.getLevelAsInt() == 30) {
					string = "Delete";
				}
				return string;
			}
		};
		table.addColumn(level, "Level");

		TextColumn<Permission> userInfo = new TextColumn<Permission>() {

			@Override
			public String getValue(Permission permission) {
				// TODO Auto-generated method stub
				return permission.getBeneficiary().getNickname();
			}
		};
		table.addColumn(userInfo, "User");

		TextColumn<Permission> sharedObject = new TextColumn<Permission>() {

			@Override
			public String getValue(Permission permission) {
				// !!!! Könnte Fehler verursachen
				// RICHITGGG Veursacht auch Fehler
				String noteB = String.valueOf(permission.getSharedObject().getType());
				String string = null;
				if (noteB == "b") {
					notebook = (NoteBook) permission.getSharedObject();
					string = "Notebook: " + notebook.getTitle();
				} else {
					note = (Note) permission.getSharedObject();
					string = "Note: " + note.getTitle();
				}
				return string;
			}
		};
		table.addColumn(sharedObject, " Object");

		table.setRowCount(permission.size(), false);
		table.setWidth("80%");
		table.setVisibleRange(0, permission.size());
		table.setRowData(0, permission);
		LayoutPanel panel = new LayoutPanel();
		panel.setSize("60em", "40em");
		panel.add(table);
		
		this.add(panel);

	}

	/**
	 * Eine Angeklickte Notiz wird angezeigt
	 */

	public void addClickNote() {
		final SingleSelectionModel<Permission> selection = new SingleSelectionModel<Permission>();
		table.setSelectionModel(selection);
		selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
	}

	private class SelectionChangeHandler implements Handler {
		private final SingleSelectionModel<Permission> selection;

		private SelectionChangeHandler(SingleSelectionModel<Permission> selection) {
			this.selection = selection;
		}

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			
			selected = selection.getSelectedObject();
			ShowThisPermission sn = new ShowThisPermission(selected);
			ApplicationPanel.getApplicationPanel().replaceContentWith(sn);

		}
	}

}
