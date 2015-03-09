package com.tsystems.javaschool.web.servlets;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        List<Contract> contracts = operatorService.findAllContracts();
        session.setAttribute("contracts", contracts);
        List<Contract> freeContracts = operatorService.findFreeContracts();
        session.setAttribute("freeContracts", freeContracts);
        List<Tariff> tariffs = operatorService.findAllTariffs();
        session.setAttribute("tariffs", tariffs);
        List<Option> allOptions = operatorService.findAllOptions();
        session.setAttribute("allOptions", allOptions);

        if (source.equals("contracts")) {
            request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").forward(request, response);
        } else if (source.equals("tariffs")) {
            request.getRequestDispatcher("/WEB-INF/JSP/admin/tariffs.jsp").forward(request, response);
        } else if (source.equals("options")) {
            request.getRequestDispatcher("/WEB-INF/JSP/admin/options.jsp").forward(request, response);
        } else if (source.equals("client")) {
            String number = request.getParameter("number");
            logger.debug("Contract number = " + number);
            Client client = operatorService.findClientByNumber(number);
            Contract contract = operatorService.findContractByNumber(number);
            Tariff tariff = contract.getTariff();
            List<Option> options = contract.getOptions();
            Date date = client.getDateOfBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateOfBirth = sdf.format(date);
            String status = "Не заблокирован";
            if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
            else if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
            session.setAttribute("client", client);
            session.setAttribute("contract", contract);
            session.setAttribute("tariff", tariff);
            session.setAttribute("options", options);
            session.setAttribute("dateOfBirth", dateOfBirth);
            session.setAttribute("status", status);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/account.jsp").forward(request, response);
        } else if (source.equals("assignNewContract")) {
            request.getRequestDispatcher("/WEB-INF/JSP/admin/assignContract.jsp").forward(request, response);
        } else if (source.equals("addNewClient")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String address = request.getParameter("address");
            String passport = request.getParameter("passport");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String[] selectedNumbers = request.getParameterValues("numbers[]");
            String newClientNumber = null;
            for (String selectedNumber : selectedNumbers) {
                for (Contract freeContract : freeContracts) {
                    if (selectedNumber.equals(freeContract.getNumber())) {
                        newClientNumber = freeContract.getNumber();
                    }
                }
            }
            String[] selectedTariffs = request.getParameterValues("tariffs[]");
            String newClientTariff = null;
            for (String selectedTariff : selectedTariffs) {
                for (Tariff tariff : tariffs) {
                    if (selectedTariff.equals(tariff.getName())) {
                        newClientTariff = tariff.getName();
                    }
                }
            }
            String[] selectedOptions = request.getParameterValues("options[]");
            List<Option> newClientOptions = new ArrayList<Option>(0);
            for (String selectedOption : selectedOptions) {
                for (Option allOption : allOptions) {
                    if (selectedOption.equals(allOption.getName())) {
                        newClientOptions.add(operatorService.findOptionByName(selectedOption));
                    }
                }
            }
            logger.debug("Выбранный номер " + newClientNumber);
            operatorService.createNewClient(firstName, lastName, dateOfBirth, address, passport, email, password);
            Client client = null;
            try {
                client = operatorService.findClientByEmailAndPassword(email, password);
            } catch (LoginException e) {
                logger.error("Creating new client error");
            }
            operatorService.setNumber(client, newClientNumber);
            Contract contract = operatorService.findContractByNumber(newClientNumber);
            logger.debug("Новый абонент: " + contract.getNumber());
            Tariff tariff = operatorService.findTariffByName(newClientTariff);
            contract.setTariff(tariff);
            logger.debug("Подключен к тарифу: " + tariff.getName());
            contract.setOptions(newClientOptions);

            freeContracts = operatorService.findFreeContracts();
            session.setAttribute("freeContracts", freeContracts);
            contracts = operatorService.findAllContracts();
            session.setAttribute("contracts", contracts);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").forward(request, response);
        } else if ((source.equals("removeClient"))) {
            try {
                String contractToRemove = request.getParameter("contractToRemove");
                Client clientToRemove = operatorService.findClientByNumber(contractToRemove);
                List<Contract> contractsToFree = clientToRemove.getContracts();
                for (Contract contract : contractsToFree) {
                    freeContracts.add(contract);
                }
                session.setAttribute("freeContracts", freeContracts);
                operatorService.removeClient(clientToRemove);
                contracts = operatorService.findAllContracts();
                session.setAttribute("contracts", contracts);
                request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").forward(request, response);
            } catch (NullPointerException e) {
                logger.debug("Номер уже удален");
            }
        } else if ((source.equals("lockContract"))) {
            String numberToLock = request.getParameter("contractNumber");
            Contract contractToLock = operatorService.findContractByNumber(numberToLock);
            operatorService.lockContract(contractToLock);
            logger.debug("Contract locked by Operator:" + contractToLock.getNumber());
            String status = "Заблокирован оператором";
            session.setAttribute("status", status);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/account.jsp").forward(request, response);
        } else if ((source.equals("unLockContract"))) {
            String numberToUnlock = request.getParameter("contractNumber");
            Contract contractToUnlock = operatorService.findContractByNumber(numberToUnlock);
            operatorService.unLockContract(contractToUnlock);
            logger.debug("Contract unlocked by Operator:" + contractToUnlock.getNumber());
            String status = "";
            if (contractToUnlock.getBlockedByClient()) status = "Заблокирован клиентом";
            else status = "Не заблокирован";
            session.setAttribute("status", status);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/account.jsp").forward(request, response);
        } else if ((source.equals("findClientByNumber"))) {
            String searchNumber = request.getParameter("searchNumber");
            logger.debug("Search for " + searchNumber);
            for (Contract contract : contracts) {
                if (searchNumber.equals(contract.getNumber())) {
                    Client client = operatorService.findClientByNumber(searchNumber);
                    contract = operatorService.findContractByNumber(searchNumber);
                    Tariff tariff = contract.getTariff();
                    List<Option> options = contract.getOptions();
                    Date date = client.getDateOfBirth();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOfBirth = sdf.format(date);
                    String status = "Не заблокирован";
                    if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
                    else if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
                    session.setAttribute("client", client);
                    session.setAttribute("contract", contract);
                    session.setAttribute("tariff", tariff);
                    session.setAttribute("options", options);
                    session.setAttribute("dateOfBirth", dateOfBirth);
                    session.setAttribute("status", status);
                    request.getRequestDispatcher("/WEB-INF/JSP/admin/account.jsp").forward(request, response);
                }
            }
            String notFound = "notFound";
            session.setAttribute("notFound", notFound);
            request.getRequestDispatcher("/WEB-INF/JSP/admin/contracts.jsp").include(request, response);
            session.setAttribute("notFound", null);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
