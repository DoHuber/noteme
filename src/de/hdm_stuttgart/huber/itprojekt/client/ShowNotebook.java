package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowNotebook extends BasicView{
	
	/**
	 * @author Erdmann, Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben, 
	 */
	private VerticalPanel vp = new VerticalPanel();
	private Button deleteBtn = new Button("Delete");
	private Button editBtn = new Button("Update");
	private Button releseBtn = new Button("Relese");

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Notizbuch xy";
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Notizbuch ";
	}

	@Override
	public void run() {

		FlowPanel contentPanel = new FlowPanel();
		vp.add(deleteBtn);
		vp.add(editBtn);
		vp.add(releseBtn);
		contentPanel.add(vp);
		RootPanel.get().add(contentPanel);

	}

}



