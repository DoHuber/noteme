package de.hdm_stuttgart.huber.itprojekt.client.gui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.ClientsideSettings;
import de.hdm_stuttgart.huber.itprojekt.shared.ReportGeneratorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.Report.HTMLReportWriter;

/**
 * Entry-Point-Klasse des Projekts <b>BankProjekt</b>.
 */
public class NoteMeReport implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;
	Button showReportButton = new Button("Alle Konten von allen Kunden");
	
	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		/*
		 * Zunächst weisen wir dem Report-Generator eine Bank-Instanz zu, die
		 * für die Darstellung der Adressdaten des Kreditinstituts benötigt
		 * wird.
		 */
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}

//		Bank bank = new Bank();
//		bank.setName("HdM Bank");
//		bank.setStreet("Nobelstr. 10");
//		bank.setZip(70569);
//		bank.setCity("Stuttgart");
//		reportGenerator.setBank(bank, new SetBankCallback());

		/*
		 * Die Reportanwendung besteht aus einem "Navigationsteil" mit der
		 * Schaltfläche zum Auslösen der Reportgenerierung und einem "Datenteil"
		 * für die HTML-Version des Reports.
		 */

	
		showReportButton.setStylePrimaryName("bankproject-menubutton");
		showReportButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reportGenerator
						.createAllNoteBooksOfAllUsersReport(new createAllNoteBooksOfAllUsersReportCallback());

			}
		});
		RootPanel.get("Navigator").add(showReportButton);
	}

	/**
	 * Diese Nested Class wird als Callback für das Setzen des Bank-Objekts bei
	 * dem ReportGenerator benötigt.
	 * 
	 * @author thies
	 * @version 1.0
	 */
	class SetBankCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			/*
			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message
			 * aus.
			 */
			ClientsideSettings.getLogger().severe(
					"Setzen der Bank fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			/*
			 * Wir erwarten diesen Ausgang, wollen aber keine Notifikation
			 * ausgeben.
			 */
		}

	}

	/**
	 * Diese Nested Class wird als Callback für das Erzeugen des
	 * AllAccountOfAllCustomersReport benötigt.
	 * 
	 * @author rathke
	 * @version 1.0
	 */
	class createAllNoteBooksOfAllUsersReportCallback implements
			AsyncCallback<AllNoteBooksOfAllUsersReport> {
		@Override
		public void onFailure(Throwable caught) {
			/*
			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message
			 * aus.
			 */
			ClientsideSettings.getLogger().severe(
					"Erzeugen des Reports fehlgeschlagen!");

		}

		@Override
		public void onSuccess(AllNoteBooksOfAllUsersReport report) {
			if (report != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(new HTML(writer.getReportText()));
			}
		}



	}

}

