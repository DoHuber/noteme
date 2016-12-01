package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IT_Projekt implements EntryPoint {
	
	public void onModuleLoad() {
		
		loadMenu();

	}
	
	/*
	 *	Navigationsmenu wird geladen  
	 *	
	 */
	private void loadMenu() {
		MenuView navigation = new MenuView();
		RootPanel.get().add(navigation);
	 	
	}
}
