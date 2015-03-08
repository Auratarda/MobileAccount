package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
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
public class AdminServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting admin servlet");
        HttpSession session = request.getSession();
        OperatorService operatorService = (OperatorService)
                request.getServletContext().getAttribute("operatorService");
        String source = request.getParameter("source");

        if (source.equals("contracts")) {
            List<Contract> contracts = operatorService.findAllContracts();
            session.setAttribute("contracts", contracts);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").forward(request, response);
        } else if (source.equals("tariffs")) {
            List<Tariff> tariffs = operatorService.findAllTariffs();
            session.setAttribute("tariffs", tariffs);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/tariffs.jsp").forward(request, response);
        } else if (source.equals("options")) {
            List<Option> options = operatorService.findAllOptions();
            session.setAttribute("options", options);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/options.jsp").forward(request, response);
        } else if (source.equals("client")) {
            String number = request.getParameter("number");
            logger.debug("Contract number = " + number);
            Client client = operatorService.findClientByNumber(number);
            session.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/account.jsp").forward(request, response);
        } else if (source.equals("assignNewContract")) {
            List<Contract> freeNumbers = operatorService.findFreeNumbers();
            session.setAttribute("freeNumbers", freeNumbers);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/assignContract.jsp").forward(request, response);
        } else if (source.equals("addNewClient")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String address = request.getParameter("address");
            String passport = request.getParameter("passport");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            operatorService.createNewClient(firstName, lastName, dateOfBirth, address, passport, email, password);
            List<Contract> contracts = operatorService.findAllContracts();
            session.setAttribute("contracts", contracts);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
