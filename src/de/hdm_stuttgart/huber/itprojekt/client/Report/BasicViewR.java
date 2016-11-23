package de.hdm_stuttgart.huber.itprojekt.client.Report;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Basisklasse nach dem Beispiel der BasicView Klasse vom Editor
 * @author dominik Erdmann
 *
 */

public abstract class BasicViewR extends FlowPanel{
	
	public void onLoad(){
		super.onLoad();
		this.add(createHeadline(getHeadlineText(), getSubHeadlineText()));
		run();
	}

	public abstract String getHeadlineText();

	public abstract String getSubHeadlineText();

	
	public HTML createHeadline(String header, String subHeader) {
		HTML headline = new HTML();
		headline.setStylePrimaryName("headline");
		headline.setHTML("<h1>" + header + "</h1><h2>" + subHeader + "</h2>");
		return headline;
	}

	public abstract void run();	
}
