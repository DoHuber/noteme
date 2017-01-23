package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Label;

public class EmbedCode extends BasicVerticalView {

    public EmbedCode() {

    }

    @Override
    public String getHeadlineText() {

        return "Embed this application";
    }

    @Override
    public String getSubHeadlineText() {

        return "Just copy & paste this HTML code in your personal homepage:";
    }

    @Override
    public void run() {


        String s = "<form action=\'http://r1-dot-it-projekt-hdm.appspot.com/it_projekt/listener\' method=\'POST\'>" +
                "<input type=\'image\' src=\'http://r1-dot-it-projekt-hdm.appspot.com/sharebutton.png\' alt=\'Share\' style=\'border:2px black solid\'>" +
                "</form>";

        Label l = new Label(s);
        this.add(l);

    }

}
