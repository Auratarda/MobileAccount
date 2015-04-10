package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.dao.ClientDAO;
import com.tsystems.javaschool.domain.dao.ContractDAO;
import com.tsystems.javaschool.domain.dao.TariffDAO;
import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.domain.entities.Contract;
import com.tsystems.javaschool.domain.entities.Role;
import com.tsystems.javaschool.domain.entities.Tariff;
import com.tsystems.javaschool.service.ClientService;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains business logic methods connected to the Client entity.
 */
@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    @Qualifier("ClientDAOImpl")
    private ClientDAO clientDAO;
    @Qualifier("TariffDAOImpl")
    @Autowired
    private TariffDAO tariffDAO;
    @Autowired
    @Qualifier("ContractDAOImpl")
    private ContractDAO contractDAO;



    /*
    * This method retrieves Client by email and password.
    **/
    @Override
    public Client login(String email, String password) throws ClientNotFoundException {
        return clientDAO.login(email, password);
    }

    /*
     * This method creates new Client with the role "CLIENT".
     **/
    @Override
    public void createNewClient(String firstName, String lastName, String dateOfBirth, String address,
                                String passport, String email, String password, String number, String selectedTariff) {
        Client client = createNewUser(firstName, lastName, dateOfBirth, address, passport, email, password);
        client = clientDAO.create(client);
        Role role = new Role("CLIENT");
        client.getRoles().add(role);
        Contract contract = contractDAO.findContractByNumber(number);
        client.getContracts().add(contract);
        contract.setClient(client);
        Tariff tariff = tariffDAO.findTariffByName(selectedTariff);
        contract.setTariff(tariff);
        contractDAO.update(contract);
        clientDAO.update(client);
    }

    /*
     * This method creates new Client with the role "ADMIN".
     **/
    @Override
    public void createNewAdmin(String firstName, String lastName, String dateOfBirth, String address, String passport, String email, String password) {
        Role role = new Role("ADMIN");
        Client admin = createNewUser(firstName, lastName, dateOfBirth, address, passport, email, password);
        admin.getRoles().add(role);
        clientDAO.create(admin);
    }

    /*
     * This method retrieves a Client by the contract number.
     **/
    @Override
    public Client findClientByNumber(String number) {
        return  contractDAO.findClientByNumber(number);
    }

    /*
     * This is an auxiliary method which creates a new Client
     * without a role and a contract.
     **/
    private Client createNewUser(String firstName, String lastName, String dateOfBirth,
                                 String address, String passport, String email, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date birthday = null;
        try {
            birthday = sdf.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Client client = new Client(firstName, lastName, birthday, address, passport, email, password);
        return client;
    }
}
