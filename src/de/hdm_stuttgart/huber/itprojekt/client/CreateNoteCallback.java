package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
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
		
		if (result.getNoteBook() != null) {
			
			ShowNotebook snb = new ShowNotebook(result.getNoteBook());
			ApplicationPanel.getApplicationPanel().replaceContentWith(snb);
			
		} else {
		
		ShowAllNotes san = new ShowAllNotes();
		ApplicationPanel.getApplicationPanel().replaceContentWith(san);
		
		}
	}

}