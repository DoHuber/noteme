package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ListBox;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class NoteBookListBox extends ListBox {
	
	private ArrayList<NoteBook> items = new ArrayList<>();

	public NoteBookListBox() {
		
	}
	
	public void addAll(List<NoteBook> l) {
		
		for (NoteBook n : l) {
			
			items.add(n);
			addItem(n);
			
		}
		
	}

	public void addItem(NoteBook nb) {
		
		items.add(nb);
		super.addItem(nb.getTitle());
		
	}
	
	public NoteBook getSelectedItem() {
		
		int index = super.getSelectedIndex();
		return items.get(index);
		
	}

}
