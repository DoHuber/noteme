package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class FloatingHeaderBar extends PopupPanel {

    public FloatingHeaderBar() {

        super(false, true);

        String height = Double.toString((Window.getClientHeight() * 0.1));
        HorizontalPanel content = new HorizontalPanel();
        content.setHeight(height + "px");

        String width = Double.toString(((Window.getClientWidth() * 0.75)));
        content.setWidth(width + "px");

        int left = (int) (Window.getClientWidth() * 0.125);
        this.setPopupPosition(left, 0);

        this.show();

    }


}
