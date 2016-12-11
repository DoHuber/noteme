package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowNote extends BasicView {
	/**
	 * @author Nikita Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben, Fähligkeitsdatum setzen -
	 * Ebene: einzelne Notizen
	 */
	private VerticalPanel vp = new VerticalPanel();
	private Button deleteBtn = new Button("Delete");
	private Button editBtn = new Button("Update");
	private Button releseBtn = new Button("Relese");
	private Button dueDateBtn = new Button("Due Date");

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Notiz xy";
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Notiz ";
	}

	@Override
	public void run() {

		FlowPanel contentPanel = new FlowPanel();
		vp.add(deleteBtn);
		vp.add(editBtn);
		vp.add(releseBtn);
		vp.add(dueDateBtn);
		contentPanel.add(vp);
		RootPanel.get().add(contentPanel);

	}

}
