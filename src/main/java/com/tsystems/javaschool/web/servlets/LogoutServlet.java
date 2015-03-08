package com.tsystems.javaschool.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            session.removeAttribute("role");
            session.invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(300, "Вы не авторизованы");
        }
    }
}
