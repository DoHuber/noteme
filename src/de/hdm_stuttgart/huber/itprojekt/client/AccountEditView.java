package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class AccountEditView extends BasicVerticalView {

    private static String logOutUrl;
    private UserInfo loggedInUser = null;
    private Button saveButton = new Button("Save changes");
    private Button deleteButton = new Button("Delete my account");
    private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
    private TextBox name = new TextBox();
    private TextBox surname = new TextBox();
    private TextBox email = new TextBox();

    public AccountEditView(UserInfo userInfo) {
        this.loggedInUser = userInfo;
    }

    public static void setLogOutUrl(String logOutUrl) {
        AccountEditView.logOutUrl = logOutUrl;
    }

    @Override
    public String getHeadlineText() {
        return "MY ACCOUNT";

    }

    @Override
    public String getSubHeadlineText() {

        return "Nickname: " + loggedInUser.getNickname();

    }

    @Override
    public void run() {

        name.setText(loggedInUser.getFirstName());
        surname.setText(loggedInUser.getSurName());
        email.setText(loggedInUser.getEmailAddress());
        email.setEnabled(false);

        deleteButton.addClickHandler(new DeleteClickHandler());
        logOutUrl = loggedInUser.getLogoutUrl();

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                saveUpdates();
            }

        });

        this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        this.setWidth("100%");
        this.add(name);
        this.add(surname);
        this.add(email);
        this.add(saveButton);
        this.add(deleteButton);

        GWT.log(loggedInUser.toString());

    }

    private void saveUpdates() {

        loggedInUser.setFirstName(name.getValue());
        loggedInUser.setSurName(surname.getValue());

        editorVerwaltung.saveUser(loggedInUser, new AsyncCallback<UserInfo>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log(caught.toString());
            }

            @Override
            public void onSuccess(UserInfo result) {
                Notificator.getNotificator().showSuccess("Changes saved");
            }

        });

    }

    private class DeleteClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            if (Window.confirm("Möchten Sie Ihren AccountEditView " + loggedInUser.getNickname() + " wirklich löschen?")) {

                logOutUrl = loggedInUser.getLogoutUrl();
                editorVerwaltung.deleteUserInfo(loggedInUser, new DeleteCallback());

            }

        }
    }

    private class DeleteCallback implements AsyncCallback<Void> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Void result) {

            Window.Location.replace(logOutUrl);

        }

    }

}
