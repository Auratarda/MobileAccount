package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

import java.util.List;

/**
 * ContractDAO.
 */
public interface ContractDAO extends GenericDao<Contract>{
    Client findClientByNumber(String number);

    Contract findContractByNumber(String number);

    List<Contract> findAllContracts();

    List<Contract> findFreeNumbers();
}
