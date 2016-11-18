package de.hdm_stuttgart.huber.itprojekt.client;

import java.awt.Checkbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author dominik erdmann
 *
 */

public class ReportFilter extends BasicView{
	
	HorizontalPanel hPanel = new HorizontalPanel();

	@Override
	public String getHeadlineText() {
		return "Report Generator Filter";
	}

	@Override
	public String getSubHeadlineText() {
		return "Bitte setzen Sie den entsprechenden Filter";
	}

	@Override
	public void run() {

		/**
		 * Filter zur Auswahl für den Report
		 */
		CheckBox cb0 = new CheckBox("Alle Notizen");
		CheckBox cb1 = new CheckBox("Alle Notizbücher");
		CheckBox cb2 = new CheckBox("Alle Nutzer");
	    cb0.setValue(true);
	    cb1.setValue(true);
	    cb2.setValue(true);

	    cb0.addClickHandler(new ClickHandler() {


		@Override
		public void onClick(ClickEvent event) {
	        boolean checked = ((CheckBox) event.getSource()).getValue();
	        Window.alert("It is " + (checked ? "" : "not ") + "checked");			
		}
	    });
	    
	    cb1.addClickHandler(new ClickHandler() {


		@Override
		public void onClick(ClickEvent event) {
	        boolean checked = ((CheckBox) event.getSource()).getValue();
	        Window.alert("It is " + (checked ? "" : "not ") + "checked");			
		}
	    });
	    cb2.addClickHandler(new ClickHandler() {


		@Override
		public void onClick(ClickEvent event) {
	        boolean checked = ((CheckBox) event.getSource()).getValue();
	        Window.alert("It is " + (checked ? "" : "not ") + "checked");			
		}
	    });
	    
	    hPanel.add(cb0);
	    hPanel.add(cb1);
	    hPanel.add(cb2);
	    RootPanel.get().add(hPanel);
	}

}
