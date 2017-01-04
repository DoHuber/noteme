package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

/**
 * Klasse die den callback zum Notiz anlegen implementiert. Die angelegte Notiz
 * wird an die EditorImpl übergeben. Später soll die angelegte Notiz noch dem
 * Nutzer angezeigt werden
 * 
 *
 */
public class CreateNoteCallback implements AsyncCallback<Note> {

	@Override
	public void onFailure(Throwable caught) {

		String message = "Fehler! Folgendes ging schief:";
		message = message + caught.toString();
		Notificator.getNotificator().showError(message);

	}

	@Override
	public void onSuccess(Note result) {
		
		Notificator.getNotificator().showSuccess("Note was created and saved.");

	}

}