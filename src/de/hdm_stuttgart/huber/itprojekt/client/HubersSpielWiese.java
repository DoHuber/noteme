package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class HubersSpielWiese {

	public HubersSpielWiese() {



	}

	public static void huberTest() {

        DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.EM);
        dockLayoutPanel.addNorth(new HTML("<b> Hier </b> steht dann ganz viel cooles!"), 4);
        dockLayoutPanel.addSouth(new HTML("FOOT"), 4);
        dockLayoutPanel.addWest(new HTML("NAVIGATION"), 10);
        dockLayoutPanel.add(new HTML("Liste mit allen posts oder sowas"));

        RootPanel.get().add(dockLayoutPanel);
    }

}
