package de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentificationAdmin;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.Report.ReportFilter;

/**
 * Entry Point für den Report Generator
 * @author dominik erdmann
 *
 */
public class ReportGenerator implements EntryPoint{
	
	public void onModuleLoad(){
		loadMenu();
	}
	
	private void loadMenu(){
		ReportFilter filter = new ReportFilter();
		RootPanel.get().add(filter);
	}

}
