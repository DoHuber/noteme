package de.hdm_stuttgart.huber.itprojekt.client.gui;


	
	import java.util.Vector;

	import com.google.gwt.user.cellview.client.DataGrid;
	import com.google.gwt.user.cellview.client.TextColumn;
	import com.google.gwt.user.client.ui.Button;
	import com.google.gwt.user.client.ui.FlowPanel;
	import com.google.gwt.user.client.ui.LayoutPanel;
	import com.google.gwt.user.client.ui.RootPanel;
	import com.google.gwt.view.client.SelectionChangeEvent;
	import com.google.gwt.view.client.SingleSelectionModel;
	import com.google.gwt.view.client.SelectionChangeEvent.Handler;

	import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
	import de.hdm_stuttgart.huber.itprojekt.client.ShowNote;

	import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
	import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
	import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
	/**
	 * Die Klasse NoteTable wird alle Notizen eines bestimmten Notizbuchs in einer Tabelle darstellen 
	 * @author Nikita Nalivayko 
	 * 
	 *
	 */

	public class PermissionTable {

		
		
		EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
		private FlowPanel fPanel = new FlowPanel();
		
		/**
		 * Funktion: Löschen, Editieren, und Freigeben - Notizbuchebene
		 */

		
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

		public FlowPanel start() {
			
			TextColumn<Permission> level = new TextColumn<Permission>() {

				@Override
				public String getValue(Permission permission) {
					
					return  Integer.toString(permission.getLevelAsInt());
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
					return permission.getSharedObject().toString();
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
			fPanel.add(panel);

			return fPanel;
		}


		/**
		 * Eine Angeklickte Notiz wird angezeigt 
		 */
//
//		public void addClickNote() {
//			final SingleSelectionModel<Note> selection = new SingleSelectionModel<Note>();
//			table.setSelectionModel(selection);
//			selection.addSelectionChangeHandler(new SelectionChangeHandler(selection));
//		}
//
//		private class SelectionChangeHandler implements Handler{
//			private final SingleSelectionModel<Note> selection;
//			private SelectionChangeHandler(SingleSelectionModel<Note> selection){
//				this.selection=selection;
//			}
//
//			@Override
//			public void onSelectionChange(SelectionChangeEvent event) {
//				selected=selection.getSelectedObject();
//				ShowNote sn = new ShowNote(selected);
//				
//				RootPanel.get("main").clear();
//				RootPanel.get("main").add(sn);
//				
//				
//			}}


}

