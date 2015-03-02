package com.tsystems.javaschool.persistence;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Util.
 */

// TODO: Why it is called hibernate?
public class HibernateUtil {
    final static Logger logger = Logger.getLogger(HibernateUtil.class);
    private static final String PERSISTENCE_UNIT = "MobileAccountPU";
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

        } catch (ExceptionInInitializerError e) {
            System.err.println("Initializing EntityManagerFactory failed:" + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
