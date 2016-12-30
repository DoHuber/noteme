package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServicesAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IT_Projekt implements EntryPoint {
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private UserInfo userInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
	      "Please sign in to your Google Account to access the cool and nice application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	private Audio bootSound;
	private Audio bootAdminSound;
	private VerticalPanel user = new VerticalPanel();
	private Label name = new Label("First Name");
	private TextBox nameBox = new TextBox();
	private Label name2 = new Label("Last Name");
	private TextBox nameBox2 = new TextBox();
	private Button btn = new Button("Save");
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
				
				if (userInfo.getFirstName()==null && userInfo.getSurName()==null){
					createUser();
				}
					
				else	loadMenu();
					
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
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(navigation);
	 	
	}
	
	private void loadLogin() {
		
		signInLink.setHref(userInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("menu").add(loginPanel);
		
	}
	
	public void createUser(){
		user.add(name);
		user.add(nameBox);
		user.add(name2);
		
		user.add(nameBox2);
		user.add(btn);
		btn.addClickHandler(new SaveClickHandler());
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(user);
	}
	private void initializeAudio() {
		
		bootSound = Audio.createIfSupported();
		bootSound.setSrc("style/audiores/startup.mp3");
		
		bootAdminSound = Audio.createIfSupported();
		bootAdminSound.setSrc("style/audiores/startupadmin.mp3");
		
	}
	private class SaveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			userInfo.setFirstName(nameBox.getText());
			userInfo.setSurName(nameBox2.getText());
			editorVerwaltung.saveUser(userInfo, new SaveCallBack());
		//	Window.alert("Yes");
			
		}
	private class SaveCallBack implements AsyncCallback<UserInfo>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(UserInfo result) {
			loadMenu();
			
		}
		
	}
		
	}


}
