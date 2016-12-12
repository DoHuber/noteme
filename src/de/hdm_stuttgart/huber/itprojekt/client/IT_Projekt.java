package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm_stuttgart.huber.itprojekt.shared.NoteMeSharedServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IT_Projekt implements EntryPoint {
	
	public void onModuleLoad() {
		
		 // getUserEmail();
		 loadMenu();

	}

	
	/*
	 *	Navigationsmenu wird geladen  
	 *	
	 */
	private void loadMenu() {
		MenuView navigation = new MenuView();
		RootPanel.get("menu").add(navigation);
	 	
	}

	private void getUserEmail() {

        NoteMeSharedServiceAsync sharedService = ClientsideSettings.getSharedService();
        sharedService.getUserEmail(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Fuck outta here!");
            }

            @Override
            public void onSuccess(String result) {
                Window.alert(result);
            }
        });
    }

}
