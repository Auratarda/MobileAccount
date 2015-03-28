package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;

import java.util.List;

/**
 * ClientDAO.
 */
public interface ClientDAO  {
    Client login(String email, String password) throws ClientNotFoundException;

    Client identify(String firstName, String lastName, String email) throws ClientNotFoundException;

    Client create(Client t);

    Client read(Long id);

    Client update(Client t);

    void delete(Client t);

    List<Client> getAll();
}
