package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.LoginException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * ClientDAOImpl.
 */
    // just delete this comments after reading
    // TODO: look what i mean:
    //  public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {
    //      public ClientDAOImpl(EntityManager entityManager) {
    //            super(entityManager);
    //      }
    //
    //      public Client login(String email, String password) throws LoginException {
    //          TypedQuery<Client> clientTypedQuery =
    //                  getEntityManager().createQuery("SELECT c FROM Client c WHERE c.email = :email " +
    //                                                         "AND c.password = :password", Client.class);
    //          clientTypedQuery.setParameter("email", email);
    //          clientTypedQuery.setParameter("password", password);
    //          if (clientTypedQuery.getResultList().isEmpty())
    //              throw new LoginException();
    //          return clientTypedQuery.getResultList().get(0);
    //      }
    //  }
    // you don't need an em, since you have it in parent

public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {
    private EntityManager em;
    public ClientDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.em = entityManager;
    }

    public Client login(String email, String password) throws LoginException {
        TypedQuery<Client> clientTypedQuery =
                em.createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        clientTypedQuery.setParameter("email", email);
        clientTypedQuery.setParameter("password", password);
        if (clientTypedQuery.getResultList().isEmpty()) throw new LoginException();
        return clientTypedQuery.getResultList().get(0);
    }
}
