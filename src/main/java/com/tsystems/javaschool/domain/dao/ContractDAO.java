package com.tsystems.javaschool.domain.dao;

import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.domain.entities.Contract;

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
