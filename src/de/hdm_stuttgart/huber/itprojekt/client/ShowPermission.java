package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.PermissionTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionServiceAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem
 * ordner zugeordnet sind.
 * 
 * @author Lisa Kuechler
 *
 */
public class ShowPermission extends BasicView {
	PermissionServiceAsync permissionVerwaltung = ClientsideSettings.getPermissionVerwaltung();
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllPermissionsCallback callback = new AllPermissionsCallback();
	UserCallback cb = new UserCallback();
	private UserInfo ui = null;
	// HorizontalPanel hPanel = new HorizontalPanel();
	private Vector<Permission> permissions = new Vector<Permission>();

	public ShowPermission(Vector<Permission> nList) {
		permissions = nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowPermission() {

	}

	// Gibt alle Notizen zurück
	public Vector<Permission> getAllPermissionsListe() {
		// new ShowAllNotes().getHeadlineText();
		return permissions;

	}

	public void setAllNotesListe(Vector<Permission> liste) {
		this.permissions = liste;

	}

	@Override
	public String getHeadlineText() {

		return "Shared with other users";
	}

	@Override
	public String getSubHeadlineText() {
		return "Click to unshare";
	}

	final Button freigabeButton = new Button("Freigegebene Notizen");
	final Button nichtFreigabeButton = new Button("Nicht freigegebene Notizen");
	final Button notizenAnzeigenButton = new Button("Alle");

	@Override
	public void run() {
		freigabeButton.setStyleName("pure-button");
		nichtFreigabeButton.setStyleName("pure-button");
		notizenAnzeigenButton.setStyleName("pure-button");
		// notizenAnzeigenButton.setPushed(true);

		FlowPanel contentPanel = new FlowPanel();
		FlowPanel fPanel2 = new FlowPanel();

		fPanel2.add(contentPanel);
		editorVerwaltung.getCurrentUser(cb);

		// NoteTable nt = new NoteTable(notes);
		// nt.addClickNote();
		RootPanel.get("main").add(contentPanel);
		// RootPanel.get("main").add(nt.start());
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();

		// freigabeButton.addClickHandler(new freigabeButtonClickHandler());
		// BUTTONS !!
	}

	private class UserCallback implements AsyncCallback<UserInfo> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(UserInfo result) {

			ui = result;
			permissionVerwaltung.getAllPermissionsCreatedBy(ui, callback);

		}

	}

	private class AllPermissionsCallback implements AsyncCallback<Vector<Permission>> {
		@Override
		public void onSuccess(Vector<Permission> result) {

			// Logger logger = Logger.getLogger("test");
			// logger.log(Level.INFO, Arrays.toString(result.toArray()));

			addPermissionsToTable(result);
		}

		@Override
		public void onFailure(Throwable caught) {

			// Logger logger = Logger.getLogger("test");
			// logger.log(Level.SEVERE, caught.toString());

		}

	}

	// @Override
	// public void run() {

	//
	//
	// hPanel.add(new HTML("<p> Hier kommt der <b>Huber</b>, <i>obacht!</i>
	// </p>"));
	//
	// AsyncCallback<Vector<Note>> callback = new AsyncCallback<Vector<Note>>()
	// {
	//
	// @Override
	// public void onFailure(Throwable caught) {
	//
	// caught.printStackTrace();
	// hPanel.add(new Label(caught.toString()));
	// }
	//
	// @Override
	// public void onSuccess(Vector<Note> result) {
	//
	// hPanel.add(new HTML("<p> Als nächstes die Hubermethode! </p>"));
	// huberMethode(result);
	//
	// }
	//
	// };
	//
	// editorVerwaltung.getAllNoteBooks(callback);
	//
	// RootPanel.get().add(hPanel);
	//
	// }

	// private void huberMethode(Vector<Note> result) {
	//
	// StringBuilder html = new StringBuilder();
	//
	// for (Note row : result) {
	//
	// html.append(row.toHtmlString() + "<br>");
	//
	// }
	//
	// RootPanel.get("main").add(new HTMLPanel(html.toString()));
	//
	// }

	public void addPermissionsToTable(Vector<Permission> result) {

		permissions = result;
		PermissionTable nt = new PermissionTable(permissions);
		nt.addClickNote();
		RootPanel.get("table").clear();
		RootPanel.get("table").add(nt.start());

	}

}