package com.tsystems.javaschool.domain.dao;

import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

import java.util.List;

/**
 * ClientDAO.
 */
public interface ClientDAO  extends GenericDao<Client>{
    Client login(String email, String password) throws ClientNotFoundException;

    Client identify(String firstName, String lastName, String email) throws ClientNotFoundException;

    List<Client> getAll();
}
