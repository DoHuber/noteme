package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationPanel extends DockLayoutPanel {
	
	private static ApplicationPanel singleton;
	
	private SimplePanel centerContent;
	private SimplePanel navigation;
	private SimplePanel header;
	private SimplePanel footer;

	protected ApplicationPanel(Unit unit) {
		super(Unit.PCT);
		
		setUpContainers();
	}

	private void setUpContainers() {
		
		centerContent = new SimplePanel();
		navigation = new SimplePanel();
		header = new SimplePanel();
		footer = new SimplePanel();
		
		header.setStyleName("panelheader");
		
		navigation.addStyleName("dockpanel");

		this.addNorth(header, 20);
		this.addSouth(footer, 10);
		this.addWest(navigation, 25);
		this.add(centerContent);
		
	}
	
	public static  ApplicationPanel getApplicationPanel() {
		
		if (singleton == null) {
			singleton = new ApplicationPanel(Unit.PCT);
		}
		
		return singleton;		
	}
	
	public void replaceContentWith(Widget w) {
		
		centerContent.remove(centerContent.getWidget());
		centerContent.setWidget(w);
		
	}

	public Widget getCenterContent() {
		return centerContent;
	}

	public void setCenterContent(Widget centerContent) {
		
		centerContent.setStyleName("dockpanel");
		this.centerContent.setWidget(centerContent);
		
	}

	
	public void setNavigation(MenuView navigation) {
		
		navigation.setStyleName("dockpanel");
		navigation.setHeight("100%");
		navigation.setWidth("100%");
		
		this.navigation.setWidget(navigation);
		
	}



	public Widget getHeader() {
		return header;
	}

	public void setHeader(Widget header) {
		
		header.setStyleName("dockpanel");
		this.header.setWidget(header);
		
	}

	public Widget getFooter() {
		return footer;
	}

	public void setFooter(Widget footer) {
		this.footer.setWidget(footer);
	}
	
}
