package de.hdm_stuttgart.huber.itprojekt.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExternalButtonListener extends HttpServlet {

    /**
     * Das hat eclipse gemacht
     */
    private static final long serialVersionUID = -5009924889428027427L;

    public ExternalButtonListener() {

    }

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
