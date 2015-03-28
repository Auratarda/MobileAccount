package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ClientDAOImpl.
 */
@Repository("ClientDAOImpl")
public class ClientDAOImpl implements ClientDAO{

    @PersistenceContext(unitName = "MobileAccountPU",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

//    @PersistenceContext(type = PersistenceContextType.EXTENDED)

//    public void setEm(EntityManager em) {
//        this.em = em;
//    }

    @Autowired
    @Qualifier("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean fb;

    @Transactional
    public Client login(String email, String password) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        clientTypedQuery.setParameter("email", email);
        clientTypedQuery.setParameter("password", password);
        List<Client> clients = clientTypedQuery.getResultList();
        if (clients.isEmpty()) throw new ClientNotFoundException();
        return clients.get(0);
    }

    @Transactional
    public Client identify(String firstName, String lastName, String email) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Client c WHERE c.firstName = :firstName " +
                        "AND c.lastName = :lastName AND c.email = :email", Client.class);
        clientTypedQuery.setParameter("firstName", firstName);
        clientTypedQuery.setParameter("lastName", lastName);
        clientTypedQuery.setParameter("email", email);
        if (clientTypedQuery.getResultList().isEmpty()) throw new ClientNotFoundException();
        return clientTypedQuery.getResultList().get(0);
    }

    @Override
    public Client create(Client t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.persist(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    @Transactional
    public Client read(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client update(Client t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.merge(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public void delete(Client t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        em.remove(t);
        entMan.getTransaction().commit();
        entMan.close();
    }

    @Override
    @Transactional
    public List<Client> getAll() {
        TypedQuery<Client> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Client c", Client.class);
        return clientTypedQuery.getResultList();
    }
}
