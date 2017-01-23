package de.hdm_stuttgart.huber.itprojekt.client.gui;


import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;

public class NotificationPopup extends PopupPanel {

    HTML myWidget;

    public NotificationPopup() {

    }

    public NotificationPopup(boolean autoHide) {
        super(autoHide);
    }

    public NotificationPopup(boolean autoHide, boolean modal) {
        super(autoHide, modal);
    }

    public NotificationPopup(String text, boolean autoHide, boolean modal) {

        super(autoHide, modal);
        this.myWidget = new HTML(text);

        int widthInPixel = (int) (Window.getClientWidth() * 0.2);
        int heightInPixel = (int) (Window.getClientHeight() * 0.05);

        setWidth(Integer.toString(widthInPixel) + "px");
        setHeight(Integer.toString(heightInPixel) + "px");
        myWidget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        setWidget(myWidget);

    }

    public void show(int seconds) {
        this.show();
        Timer t = new Timer() {

            @Override
            public void run() {
                NotificationPopup.this.hide();
            }

        };

        t.schedule(seconds * 1000);

    }

    public class OnCloseHandler implements CloseHandler<PopupPanel> {

        @Override
        public void onClose(CloseEvent<PopupPanel> event) {
            Notificator.getNotificator().removeTimedOut(NotificationPopup.this);
        }
    }

}
