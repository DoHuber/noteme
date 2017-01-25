package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Herzstück der Gui, dieses erweiterte <code>DockLayoutPanel</code> stellt die Applikation dar.
 * Dabei werden feste Methoden geliefert, mit denen Navigation, Header und Footer gesetzt werden können.
 * Weiterhin kann der zentrale Inhalt immer laufend ersetzt werden.
 */
public class ApplicationPanel extends DockLayoutPanel {

    private static ApplicationPanel singleton;

    private ScrollPanel centerContent;
    private SimplePanel navigation;
    private SimplePanel header;
    private SimplePanel footer;

    protected ApplicationPanel(Unit unit) {
        super(Unit.PCT);

        setUpContainers();
    }

    public static ApplicationPanel getApplicationPanel() {

        if (singleton == null) {
            singleton = new ApplicationPanel(Unit.PCT);
        }

        return singleton;
    }

  
    private void setUpContainers() {

        centerContent = new ScrollPanel();
        navigation = new SimplePanel();
        header = new SimplePanel();
        footer = new SimplePanel();

        header.setStyleName("bordered");
        navigation.setStyleName("navigation");
        header.setStyleName("bordered");
        footer.setStyleName("bordered");

        this.addNorth(header, 20);
        this.addSouth(footer, 10);
        this.addWest(navigation, 20);
        this.add(centerContent);

    }

    public void replaceContentWith(Widget w) {

        if (centerContent.getWidget() != null) {
            centerContent.remove(centerContent.getWidget());
        }

        centerContent.setWidget(w);

    }

    public Widget getCenterContent() {
        return centerContent;
    }

    public void setCenterContent(Widget centerContent) {

        this.centerContent.setWidget(centerContent);

    }


    public void setNavigation(Widget navigation) {

        navigation.setWidth("100%");
        this.navigation.setWidget(navigation);

    }


    public Widget getHeader() {
        return header;
    }

    public void setHeader(Widget header) {

        header.setHeight("100%");
        header.setWidth("100%");

        this.header.setWidget(header);

    }

    public Widget getFooter() {
        return footer;
    }

    public void setFooter(Widget footer) {

        footer.setHeight("100%");
        footer.setWidth("100%");

        this.footer.setWidget(footer);
    }

}
