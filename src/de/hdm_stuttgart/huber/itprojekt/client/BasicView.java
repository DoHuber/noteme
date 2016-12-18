package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Basisklasse 
 * @author Nikita Nalivayko
 *
 */
public abstract class BasicView extends FlowPanel {

	/**
	 * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, sas
	 * geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht wird.
	 */
	@Override
	public void onLoad() {
		super.onLoad();
	//	RootPanel.get("main").clear();
		this.add(createHeadline(getHeadlineText(), getSubHeadlineText()));
		
		run();

	}

	

	/**
	 * 
	 * Mit Hilfe dieser Methode erstellen wir aus einem String ein mittels CSS
	 * formatierbares HTML-Element. Unter CSS lässt sich das Ergebnis über
	 * <code>.headline</code> referenzieren bzw. formatieren.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param header
	 * @param subHeader
	 * @return
	 */
	public HTML createHeadline(String header, String subHeader) {
		HTML headline = new HTML();
		headline.setStylePrimaryName("headline");
		headline.setHTML("<h1>" + header + "</h1><h2>" + subHeader + "</h2>");
		return headline;
	}
	/**
	 * Abstrakte Einschubmethoden, die in den Subklassen zu realisieren sind. 
	 */
	
	public abstract String getHeadlineText();

	public abstract String getSubHeadlineText();

	public abstract void run();

}
