package com.tsystems.javaschool.test;

import com.tsystems.javaschool.exceptions.TariffNotSupportedOptionException;
import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.Impl.ClientServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * AppTest.
 */
public class AppTest {
    final static Logger logger = Logger.getLogger(AppTest.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = PersistenceUtil.getEntityManagerFactory();
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            ClientService clientService = new ClientServiceImpl(em);
            clientService.changeTariff("1", "2");

            em.getTransaction().commit();
            logger.info("Commit success!");
        } catch (TariffNotSupportedOptionException e) {
            logger.error("Couldn't change the tarrif! " + e.getMessage());
        } catch (Exception e) {
            logger.error("Sorry, something wrong!", e);
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
