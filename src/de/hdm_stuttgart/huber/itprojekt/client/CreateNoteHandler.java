package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

class CreateNoteHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

        CreateNote cN = new CreateNote();
        ApplicationPanel.getApplicationPanel().replaceContentWith(cN);

    }
}