package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.ContractIsBlockedException;
import com.tsystems.javaschool.exceptions.IncompatibleOptionException;
import com.tsystems.javaschool.exceptions.RequiredOptionException;
import com.tsystems.javaschool.exceptions.TariffNotSupportedOptionException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
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
public class ClientServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting client servlet");
        HttpSession session = request.getSession();
        OperatorService operatorService = (OperatorService)
                request.getServletContext().getAttribute("operatorService");
        ClientService clientService = (ClientService)
                request.getServletContext().getAttribute("clientService");
        Client client = (Client) session.getAttribute("client");
        List<Contract> contracts = client.getContracts();
        Contract contract = contracts.get(0);
        session.setAttribute("contract", contract);
        Tariff tariff = contract.getTariff();
        session.setAttribute("tariff", tariff);
        List<Option> options = contract.getOptions();
        session.setAttribute("options", options);
        String source = request.getParameter("source");

        if (source.equals("personal")) {
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/JSP/client.jsp");
            view.forward(request, response);
        } else if (source.equals("contract")) {
            request.getRequestDispatcher("/WEB-INF/JSP/client/contract.jsp").forward(request, response);
        } else if (source.equals("tariffs")) {
            List<Tariff> tariffs = operatorService.findAllTariffs();
            Tariff currentTarrif = contract.getTariff();
            tariffs.remove(currentTarrif);
            session.setAttribute("tariffs", tariffs);
            request.getRequestDispatcher("/WEB-INF/JSP/client/tariff.jsp").forward(request, response);
        } else if (source.equals("options")) {
            List<Option> allOptions = operatorService.findAllOptions();
            session.setAttribute("allOptions", allOptions);
            request.getRequestDispatcher("/WEB-INF/JSP/client/option.jsp").forward(request, response);
        } else if (source.equals("changeTariff")) {
            String tariffName = request.getParameter("tariffName");
            String contractNumber = contract.getNumber();
            try {
                clientService.changeTariff(contractNumber, tariffName);
                tariff = contract.getTariff();
                session.setAttribute("tariff", tariff);
                request.getRequestDispatcher("/WEB-INF/JSP/client/contract.jsp").forward(request, response);
            } catch (ContractIsBlockedException e) {
                e.printStackTrace();
            } catch (TariffNotSupportedOptionException e) {
                e.printStackTrace();
            }
        } else if (source.equals("addOption")) {
            String optionName = request.getParameter("optionName");
            String contractNumber = contract.getNumber();
            try {
                clientService.addOption(contractNumber, optionName);
                request.getRequestDispatcher("/WEB-INF/JSP/client/contract.jsp").forward(request, response);
            } catch (ContractIsBlockedException e) {
                e.printStackTrace();
            } catch (IncompatibleOptionException e) {
                e.printStackTrace();
            } catch (RequiredOptionException e) {
                e.printStackTrace();
            }
        } else if (source.equals("removeOption")) {
            String optionName = request.getParameter("optionName");
            String contractNumber = contract.getNumber();
            try {
                clientService.removeOption(contractNumber, optionName);
                request.getRequestDispatcher("/WEB-INF/JSP/client/contract.jsp").forward(request, response);
            } catch (ContractIsBlockedException e) {
                e.printStackTrace();
            } catch (RequiredOptionException e) {
                e.printStackTrace();
            }
        } else if (source.equals("lockContract")) {
            String contractNumber = request.getParameter("contractNumber");
            clientService.lockContract(contractNumber);
            request.getRequestDispatcher("/WEB-INF/JSP/client/contractLockedByClient.jsp").forward(request, response);
        } else if (source.equals("unLockContract")) {
            String contractNumber = request.getParameter("contractNumber");
            clientService.unLockContract(contractNumber);
            request.getRequestDispatcher("/WEB-INF/JSP/client/contract.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
