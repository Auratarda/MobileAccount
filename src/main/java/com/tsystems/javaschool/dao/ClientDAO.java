package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;

/**
 * ClientDAO.
 */
public interface ClientDAO  extends GenericDao<Client>{
    Client login(String email, String password) throws ClientNotFoundException;

    Client identify(String firstName, String lastName, String email) throws ClientNotFoundException;
}
