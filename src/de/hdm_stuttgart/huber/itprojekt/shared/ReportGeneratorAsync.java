package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNoteBooksOfAllUsersReport;
import de.hdm_stuttgart.huber.itprojekt.shared.report.AllNoteBooksOfUserReport;




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
      AsyncCallback<AllNoteBooksOfAllUsersReport> callback);

  void createAllNoteBooksOfUserReport(UserInfo u,
      AsyncCallback<AllNoteBooksOfUserReport> callback);

  void init(AsyncCallback<Void> callback);

  void create(UserInfo uI, AsyncCallback<Void> callback);

}

