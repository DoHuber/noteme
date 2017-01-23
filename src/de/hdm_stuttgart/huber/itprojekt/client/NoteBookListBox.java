package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.ListBox;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

import java.util.ArrayList;
import java.util.List;

public class NoteBookListBox extends ListBox {

    private ArrayList<Notebook> items = new ArrayList<>();

    public NoteBookListBox() {

    }

    public void addAll(List<Notebook> l) {

        for (Notebook n : l) {

            items.add(n);
            addItem(n);

        }

    }

    public void addItem(Notebook nb) {

        items.add(nb);
        super.addItem(nb.getTitle());

    }

    public Notebook getSelectedItem() {

    	String title = super.getValue(super.getSelectedIndex());
    	
        // Unperformant, aber die davorige Lösung hat nicht funktioniert..
    	for (Notebook n : items) {
    		if (n.getTitle().equals(title)) {
    			return n;
    		}
    	}
    	
    	throw new RuntimeException("Notebook existiert nicht");

    }

}
