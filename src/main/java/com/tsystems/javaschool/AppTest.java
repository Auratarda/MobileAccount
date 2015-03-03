package com.tsystems.javaschool;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.ClientServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

/**
 * Test.
 */
public class AppTest {
    final static Logger logger = Logger.getLogger(AppTest.class);
    public static void main(String[] args) {
        EntityManagerFactory emf = PersistenceUtil.getEntityManagerFactory();
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            ClientService clientService = new ClientServiceImpl(em);
            Client client = clientService.login("ivan@yandex.ru", "ivan");
            System.out.println(client);

            em.getTransaction().commit();
            logger.info("Commit success!");
        } catch (Exception e) {
            logger.error("Sorry, something wrong!", e);
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static Client createClient(){
        String firstName = "Fedorov";
        String lastName = "Fedor";
        Date dateOfBirth = new Date();
        String address = "Holodnaya, 5";
        String passport = "RUS";
        String email = "fedorr@yandex.ru";
        String password = "fedor";

        Client newClient = new Client(firstName, lastName, dateOfBirth, address, passport, email, password);
        return newClient;
    }

    private static Contract createContract() {
        String number = "9048888888";
        boolean blockedByClient = false;
        boolean blockedByOperator = true;
        Contract newContract = new Contract(number, blockedByClient, blockedByOperator);
        return newContract;
    }

    private static Tariff createTariff() {
        String name = "Delovoi";
        long price = 700L;
        Tariff newTariff = new Tariff(name, price);
        return newTariff;
    }

    private static Option createOption(){
        String name = "Perezvoni mne";
        long optionPrice = 5L;
        long connectionCost = 10L;
        Option newOption = new Option(name, optionPrice, connectionCost);
        return newOption;
    }

    private static void setNumber(Client client, Contract contract) {
        contract.setClient(client);
        client.getContracts().add(contract);
    }

    private static void setTariff(Contract contract, Tariff tariff) {
        contract.setTariff(tariff);
    }
}
