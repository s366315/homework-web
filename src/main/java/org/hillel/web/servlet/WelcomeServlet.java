package org.hillel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("token");
        if (token == null) {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        } else {
            resp.getWriter().println("<html><body><h1>Hello " + token + "!</h1></body></html>");
        }
    }
}
