package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
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
		setWidth("20%");
		setHeight("5%");
		myWidget.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(myWidget);
		
	}
	
	public void show(int seconds) {
		this.show();
		Timer t = new Timer(){
			
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
