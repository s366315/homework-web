package org.hillel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(urlPatterns = "/auth", name = "authServlet", loadOnStartup = 1)
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userNameParam");
        String password = req.getParameter("passwordParam");
        HttpSession session = req.getSession();
        session.setAttribute("token", userName + ":" + password);
        session.setAttribute("userNameParam", userName);
        session.setAttribute("passwordParam", password);
        resp.sendRedirect("/welcome");
    }
}
