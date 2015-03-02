package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;

/**
 * ClientService.
 */
public interface ClientService {

    /**
     * Basic methods.
     */
    void createClient(Client client);

    Client readClient(Long id);

    void updateClient(Client client);

    void deleteClient(Client client);

    /**Specific methods. */
}
