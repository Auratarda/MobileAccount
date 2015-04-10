package com.tsystems.javaschool.service;

import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

/**
 * Created by Stanislav on 09.04.2015.
 */
public interface ClientService {
    Client login(String email, String password) throws ClientNotFoundException;

    void createNewAdmin(String firstName, String lastName, String dateOfBirth,
                        String address, String passport, String email, String password);

    void createNewClient(String firstName, String lastName, String dateOfBirth,
                         String address, String passport, String email, String password,
                         String number, String tariff);

    Client findClientByNumber(String number);
}
