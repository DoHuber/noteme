package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllAccountsOfAllCustomersReport;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.AllAccountsOfCustomerReport;
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

  void createAllAccountsOfAllCustomersReport(
      AsyncCallback<AllAccountsOfAllCustomersReport> callback);

  void createAllAccountsOfCustomerReport(UserInfo u,
      AsyncCallback<AllAccountsOfCustomerReport> callback);

  void init(AsyncCallback<Void> callback);

  void setUser(UserInfo u, AsyncCallback<Void> callback);

}

