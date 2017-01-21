package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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
public class ShowAllPermissions extends BasicVerticalView {
	PermissionServiceAsync permissionVerwaltung = ClientsideSettings.getPermissionVerwaltung();
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllPermissionsCallback callback = new AllPermissionsCallback();
	UserCallback cb = new UserCallback();
	private UserInfo ui = null;
	private Vector<Permission> permissions = new Vector<Permission>();
	
	private PermissionTable currentTable;

	public ShowAllPermissions(Vector<Permission> nList) {
		permissions = nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllPermissions() {

	}

	// Gibt alle Notizen zurück
	public Vector<Permission> getAllPermissionsListe() {
		
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

		FlowPanel contentPanel = new FlowPanel();
		FlowPanel fPanel2 = new FlowPanel();

		fPanel2.add(contentPanel);
		editorVerwaltung.getCurrentUser(cb);

		this.add(contentPanel);

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
			
			addPermissionsToTable(result);
			
		}

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

	}

	public void addPermissionsToTable(Vector<Permission> permissions) {
		
		if (currentTable != null) {
			this.remove(currentTable);
		}

		PermissionTable permissionTable = new PermissionTable(permissions);
		permissionTable.addClickNote();
		
		this.add(permissionTable);
		currentTable = permissionTable;

	}

}