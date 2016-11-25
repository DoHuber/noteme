package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

/**
 * 
 * Eine Klasse zum Test der Client-Server Kommunikation Wird später gelöscht.
 *
 * 
 */

public class HelloWorld extends BasicView {
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	HorizontalPanel hPanel = new HorizontalPanel();
	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "HelloWorld Test ";
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "GetHelloWorld Methode der Klasse EditorImpl wird getestet" ;
	}

	@Override
	public void run() {
		
		
		sayHello();
		
		
	}


	private void  sayHello() {
		editorVerwaltung.getHelloWorld(new HelloCallback());
		
		
	}
	
	private class HelloCallback implements AsyncCallback<String>{

		@Override
		public void onFailure(Throwable caught) {
			RootPanel.get().clear();
			
		}

		@Override
		public void onSuccess(String result) {
			String hello =result;
			hPanel.add(new HTML(hello));
			RootPanel.get().add(hPanel);
			
		}
		
	}

}
