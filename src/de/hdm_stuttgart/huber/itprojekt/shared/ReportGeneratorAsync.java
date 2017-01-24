package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.DateFilterable.DateType;
import de.hdm_stuttgart.huber.itprojekt.shared.report.*;

import java.sql.Date;
import java.util.Map;

/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 *
 * @author thies
 */
public interface ReportGeneratorAsync {

    void createAllNotebooksR(AsyncCallback<AllNotebooksR> callback);

    void createAllUserNotebooksR(AsyncCallback<AllUserNotebooksR> callback);

    void createAllUserNotesR(AsyncCallback<AllUserNotesR> callback);

    void createAllNotesR(AsyncCallback<AllNotesR> callback);

    void createAllPermissionsR(AsyncCallback<AllPermissionsR> callback);

    void init(AsyncCallback<Void> callback);

    void createAllUserPermissionsR(AsyncCallback<AllUserPermissionsR> callback);

    void createCustomReport(String type, String userEmail, Map<String, Date> timespan, boolean includePermissions,
			DateType dateType, AsyncCallback<CustomReport> callback);

}
