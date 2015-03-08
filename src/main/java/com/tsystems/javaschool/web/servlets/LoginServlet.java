package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Role;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * LoginServlet.
 */
public class LoginServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting login servlet");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        OperatorService operatorService = (OperatorService)
                request.getServletContext().getAttribute("operatorService");
        ClientService clientService = (ClientService)
                request.getServletContext().getAttribute("clientService");
        Client client;
        try {
            client = clientService.login(email, password);
            String uid = "" + client.getId();
            logger.debug("UserID = " + uid);
            session.setAttribute("client", client);
            List<Role> roles = client.getRoles();
            session.setAttribute("roles", roles);
            for (Role role : roles) {
                if (role.getRole().equals("ADMIN")) {
                    List<Contract> contracts = operatorService.findAllContracts();
                    List<Tariff> tariffs = operatorService.findAllTariffs();
                    session.setAttribute("contracts", contracts);
                    session.setAttribute("tariffs", tariffs);
                    request.getRequestDispatcher("/WEB-INF/JSP/admin.jsp").forward(request, response);
                }
                if (role.getRole().equals("CLIENT")) {
                    request.getRequestDispatcher("/WEB-INF/JSP/client.jsp").forward(request, response);
                }
            }
        } catch (LoginException e) {
            logger.debug("Client is not found");
            request.getRequestDispatcher("/WEB-INF/JSP/loginError.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
