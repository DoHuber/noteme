package de.hdm_stuttgart.huber.itprojekt.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import java.util.ArrayList;

public class Notificator {

    private static Notificator singleton;
    private ArrayList<NotificationPopup> activeNotifications;

    /**
     * Singleton, verwaltet Notifications für eine Sitzung
     * Muss ein Singleton sein, da sonst das Stapelfeauture für mehrere Benachrichtigungen
     * zur gleichen Zeit nicht funktioniert.
     *
     * @author Dominik Huber
     */

    protected Notificator() {

        activeNotifications = new ArrayList<>();

    }

    public static Notificator getNotificator() {

        if (singleton == null) {
            singleton = new Notificator();
        }

        return singleton;

    }

    public void showSuccess(String text) {

        text = "<p style=\"color:green\">" + text + "</p>";
        NotificationPopup widget = new NotificationPopup(text, false, false);
        widget.setTitle("Success");
        positionAndShow(widget, 5);

    }

    public void showError(String text) {

        text = "<p style=\"color:red\">" + text + "</p>";
        NotificationPopup widget = new NotificationPopup(text, true, false);
        widget.setTitle("Error");
        positionAndShow(widget, 10);

    }

    private void positionAndShow(NotificationPopup p, int seconds) {

        p.addCloseHandler(p.new OnCloseHandler());

        if (activeNotifications.isEmpty()) {

            int left = (int) (Window.getClientWidth() * 0.75);
            int top = (int) (Window.getClientHeight() * 0.85);

            p.setPopupPosition(left, top);

            GWT.log("Active determined empty");

        } else {

            GWT.log("Active determinded not empty.");
            NotificationPopup target = activeNotifications.get(activeNotifications.size() - 1);
            p.showRelativeTo(target);

        }

        p.show(seconds);
        activeNotifications.add(p);

    }

    public void removeTimedOut(NotificationPopup toRemove) {

        if (activeNotifications.contains(toRemove)) {
            activeNotifications.remove(toRemove);
        }

    }

}
