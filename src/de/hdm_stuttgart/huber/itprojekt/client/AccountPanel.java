package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class AccountPanel extends PopupPanel {

    private UserInfo loggedInUser;

    public AccountPanel(UserInfo loggedInUser) {
        super(true);
        this.loggedInUser = loggedInUser;
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        VerticalPanel contentPanel = new VerticalPanel();

        Anchor myAccountAnchor = new Anchor("My AccountEditView");
        myAccountAnchor.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                ApplicationPanel.getApplicationPanel().replaceContentWith(new AccountEditView(loggedInUser));

            }

        });

        contentPanel.add(myAccountAnchor);

        Anchor embedCodeAnchor = new Anchor("Embed this");
        embedCodeAnchor.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                ApplicationPanel.getApplicationPanel().replaceContentWith(new EmbedCode());

            }

        });

        contentPanel.add(embedCodeAnchor);

        Anchor logOutAnchor = new Anchor("Log out");
        logOutAnchor.setHref(loggedInUser.getLogoutUrl());
        contentPanel.add(logOutAnchor);

        this.add(contentPanel);

    }


}
