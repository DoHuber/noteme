package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllNoteBooksOfAllUsers;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllNoteBooksOfUserReport;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;




/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 */
public interface ReportGeneratorAsync {

  void createAllNoteBooksOfAllUsersReport(
      AsyncCallback<AllNoteBooksOfAllUsers> callback);

  void createAllNoteBooksOfUserReport(UserInfo u,
      AsyncCallback<AllNoteBooksOfUserReport> callback);

  void init(AsyncCallback<Void> callback);

  void create(UserInfo uI, AsyncCallback<Void> callback);

}

