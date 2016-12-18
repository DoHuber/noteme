package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IT_Projekt implements EntryPoint {
	
	private UserInfo userInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
	      "Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	public void onModuleLoad() {
		
		SharedServicesAsync loginService = GWT.create(SharedServices.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {
			
			public void onFailure(Throwable e) {
				
			}
			
			public void onSuccess(UserInfo result) {
				
				userInfo = result;
				
				if (userInfo.isLoggedIn()) {
					loadMenu();
				} else {
					loadLogin();
				}
				
			}
		
		});
		

	}

	/*
	 *	Navigationsmenu wird geladen  
	 *	
	 */
	private void loadMenu() {
		MenuView navigation = new MenuView();
		Anchor a = new Anchor("Log out");
		a.setHref(userInfo.getLogoutUrl());
		navigation.setLogoutAnchor(a);
		RootPanel.get("menu").add(navigation);
	 	
	}
	
	private void loadLogin() {
		
		signInLink.setHref(userInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("menu").add(loginPanel);
		
	}
	

}
