package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
/**
 * Notizanlegen 
 * @author Nikita Nalivayko
 *
 */
public class CreateNote extends BasicView {

	
	private RichTextArea noteArea 			= new RichTextArea();
	private VerticalPanel vPanel 			= new VerticalPanel();
	private HorizontalPanel hPanel 		= new HorizontalPanel();
	private TextBox  	titleTextBox 		= new TextBox();
	private TextBox  	subTitleTextBox 	= new TextBox();
	private Button 		createButton 		= new Button("Create");
	private DateBox 	dueDateBox			= new DateBox();
	private Label 				title 				= new Label("Title");
	private Label 				subtitle			= new Label("Subtitle");
	private Label 				dueDate 			= new Label("Due Date");
	
	
	
	

	@Override
	public void run() {
/*
 * Widgets  
 * 
 * */
		vPanel.add(title);
		vPanel.add(titleTextBox);
		
		vPanel.add(subtitle);
		vPanel.add(subTitleTextBox);
		
		vPanel.add(dueDate);
		vPanel.add(dueDateBox);
		
		vPanel.add(createButton);
		hPanel.add(vPanel);
		hPanel.add(noteArea);
		RootPanel.get().add(hPanel);
		
		
		
	}




	@Override
	public String getHeadlineText() {
		
		return "Create New Note";
	}
	@Override
	public String getSubHeadlineText() {
	
		return "Sinnvoller Text!";
	}

}
