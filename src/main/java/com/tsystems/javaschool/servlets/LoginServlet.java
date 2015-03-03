package com.tsystems.javaschool.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.ClientServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        ClientService clientService = new ClientServiceImpl(em);
        Client client;
        try {
            logger.debug("Attempting to log in");   // TODO: no useful information, dublicate of event
            client = clientService.login(email, password);
            String firstName = client.getFirstName();
            String lastName = client.getLastName();
            Date date = client.getDateOfBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateOfBirth = sdf.format(date);
            String address = client.getAddress();
            String passport = client.getPassport();
            email = client.getEmail();
            request.getSession().setAttribute("firstName", firstName);
            request.getSession().setAttribute("lastName", lastName);
            request.getSession().setAttribute("dateOfBirth", dateOfBirth);
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("address", address);
            request.getSession().setAttribute("passport", passport);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/client.jsp");
            view.forward(request, response);
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
