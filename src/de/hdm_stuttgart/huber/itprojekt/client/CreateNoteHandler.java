package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

class CreateNoteHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {

		CreateNote cN = new CreateNote();
		RootPanel.get("main").clear();
		RootPanel.get("main").add(cN);
		
	}
}