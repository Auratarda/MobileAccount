package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.LoginException;

/**
 * ClientDAO.
 */
public interface ClientDAO extends GenericDAO<Client, Long> {
    Client login(String email, String password) throws LoginException;
}
