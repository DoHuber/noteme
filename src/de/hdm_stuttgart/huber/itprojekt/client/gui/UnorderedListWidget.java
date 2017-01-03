package de.hdm_stuttgart.huber.itprojekt.client.gui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Nikita Nalivayko, Dominik Erdmann
 *
 */
public class UnorderedListWidget extends ComplexPanel {

	public UnorderedListWidget() {
		this.setElement(Document.get().createULElement());
	}

	public void setId(String id) {
		// Set an attribute common to all tags
		getElement().setId(id);
	}

	public void setDir(String dir) {
		// Set an attribute specific to this tag
		((UListElement) getElement().cast()).setDir(dir);
	}

	@SuppressWarnings("deprecation")
	@Override

	public void add(Widget w) {
		// ComplexPanel requires the two-arg add() method
		super.add(w, getElement());
	}

}
