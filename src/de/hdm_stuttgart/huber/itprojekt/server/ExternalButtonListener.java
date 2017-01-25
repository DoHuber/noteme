package de.hdm_stuttgart.huber.itprojekt.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Java SE-Servlet, welches für unter http://host/it_projekt/listener eingehende HTTP POST-Requests verantwortlich ist,
 * Teil der <i>externer Button</i>-Funktionalität
 */
public class ExternalButtonListener extends HttpServlet {

    /**
     * Das hat eclipse gemacht
     */
    private static final long serialVersionUID = -5009924889428027427L;

    public ExternalButtonListener() {

    }

    /**
     * Nach dem Klick auf den externen Button wird der Browser des Nutzers einen HTTP POST - Request an diese URL
     * ausführen. Dieses Servlet startet eine Session, in welcher der <code>referer</code> aus dem Requestheader
     * gespeichert wird, anschließend wird der Nutzer an die Applikation weitergeleitet. (via HTTP 302 Found)
     *
     * @param request eingehender HTTP-Request
     * @param response ausgehende HTTP Response
     * @throws ServletException Wenn serverseitige Fehler auftreten
     * @throws IOException Bei ungültigen Requests
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String remoteHost = request.getHeader("referer");
        HttpSession session = request.getSession();
        session.setAttribute("source", remoteHost);
        
        System.out.println("RemoteHost found to be: " + remoteHost);

        response.sendRedirect(response.encodeRedirectURL("/IT_Projekt.html"));

    }

}
