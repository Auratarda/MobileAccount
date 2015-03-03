package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;

/**
 * ClientDAO.
 */
public interface ClientDAO extends GenericDAO<Client, Long> {
    Client login(String email, String password);
}
