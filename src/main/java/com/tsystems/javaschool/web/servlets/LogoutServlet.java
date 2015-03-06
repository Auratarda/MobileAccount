package com.tsystems.javaschool.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        request.getRequestDispatcher("index.jsp").include(request, response);

        HttpSession session = request.getSession();
        session.invalidate();

        out.print("Вы успешно вышли из личного кабинета!");

        out.close();
    }
}
