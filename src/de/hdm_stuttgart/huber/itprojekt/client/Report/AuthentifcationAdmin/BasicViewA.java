package de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentifcationAdmin;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class BasicViewA extends FlowPanel{
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
