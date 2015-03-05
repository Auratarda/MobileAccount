package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.ClientServiceImpl;
import com.tsystems.javaschool.services.OperatorService;
import com.tsystems.javaschool.services.OperatorServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * LoginServlet.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting login servlet");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        EntityManager em = PersistenceUtil.getEntityManager();
        OperatorService operatorService = new OperatorServiceImpl(em);
        ClientService clientService = new ClientServiceImpl(em);
        Client client;
        try {
            client = clientService.login(email, password);
            String id = "" + client.getId();
            String firstName = client.getFirstName();
            String lastName = client.getLastName();
            Date date = client.getDateOfBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateOfBirth = sdf.format(date);
            String address = client.getAddress();
            String passport = client.getPassport();
            email = client.getEmail();
            HttpSession session = request.getSession();
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
            session.setAttribute("dateOfBirth", dateOfBirth);
            session.setAttribute("email", email);
            session.setAttribute("address", address);
            session.setAttribute("passport", passport);
            Cookie userID = new Cookie("id", id);
            userID.setMaxAge(24 * 60 * 60);
            response.addCookie(userID);
            if (email.equals("sidor@ya.ru") && password.equals("sidor")) {
                List<Contract> contracts = operatorService.findAllContracts();
                List<Tariff> tariffs = operatorService.findAllTariffs();
                session.setAttribute("contracts", contracts);
                session.setAttribute("tariffs", tariffs);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/admin.jsp");
                view.forward(request, response);
            } else {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/client.jsp");
                view.forward(request, response);
            }
        } catch (LoginException e) {
            logger.debug("Client is not found");
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/loginError.jsp");
            view.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
