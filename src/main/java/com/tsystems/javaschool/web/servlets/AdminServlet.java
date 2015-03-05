package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.OperatorService;
import com.tsystems.javaschool.services.OperatorServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * LoginServlet.
 */
@WebServlet(name = "LoginServlet")
public class AdminServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting admin servlet");
        HttpSession session = request.getSession();
        EntityManager em = PersistenceUtil.getEntityManager();
        OperatorService operatorService = new OperatorServiceImpl(em);

        List<Contract> contracts = operatorService.findAllContracts();
        session.setAttribute("contracts", contracts);
        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/admin.jsp");
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
