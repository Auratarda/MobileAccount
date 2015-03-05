package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

/**
 * ContractDAO.
 */
public interface ContractDAO extends GenericDAO<Contract, Long> {
    Client findClientByNumber(String number);
}
