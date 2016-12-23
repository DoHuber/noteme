package de.hdm_stuttgart.huber.itprojekt.client;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Window;
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
	      "Please sign in to your Google Account to access the cool and nice application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	private Audio bootSound;
	private Audio bootAdminSound;
	
	public void onModuleLoad() {
		
		
		SharedServicesAsync loginService = GWT.create(SharedServices.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {
			
			public void onFailure(Throwable e) {
				
			}
			
			public void onSuccess(UserInfo result) {
				
				userInfo = result;
				
				if (userInfo.isLoggedIn()) {
					
					initializeAudio();
					
					if (!userInfo.isAdmin()) {
						
						IT_Projekt.this.bootSound.play();
						
					} else {
						
						IT_Projekt.this.bootAdminSound.play();
						
					}
					
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
		MenuView.setLogOutUrl(userInfo.getLogoutUrl());
		RootPanel.get("menu").add(navigation);
	 	
	}
	
	private void loadLogin() {
		
		signInLink.setHref(userInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("menu").add(loginPanel);
		
	}
	
	private void initializeAudio() {
		
		bootSound = Audio.createIfSupported();
		bootSound.setSrc("style/audiores/startup.mp3");
		
		bootAdminSound = Audio.createIfSupported();
		bootAdminSound.setSrc("style/audiores/startupadmin.mp3");
		
	}
	

}
