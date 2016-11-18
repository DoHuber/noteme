package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Notizbuch anlegen
 * 
 * @author Nikita Nalivayko
 *
 */
public class CreateNotebook extends BasicView {

	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox titleTextBox = new TextBox();
	private TextBox subTitleTextBox = new TextBox();
	private Button createButton = new Button("Create");
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	// Label lb = new Label("Hier wird ein neues Notizbuch erstellt ");

	@Override
	public void run() {
		/*
		 * Notizbuch anlegen Widgets
		 * 
		 */
		vPanel.add(title);
		vPanel.add(titleTextBox);
		vPanel.add(subtitle);
		vPanel.add(subTitleTextBox);
		vPanel.add(createButton);
		RootPanel.get().add(vPanel);

	}

	@Override
	public String getHeadlineText() {

		return "Create New Notebook";
	}

	@Override
	public String getSubHeadlineText() {

		return "Sinnvoller Text!";
	}

}
