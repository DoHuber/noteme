package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ListBox;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

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

		int index = super.getSelectedIndex();
		return items.get(index);

	}

}
