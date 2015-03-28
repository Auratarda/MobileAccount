package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

import java.util.List;

/**
 * ContractDAO.
 */
public interface ContractDAO {
    Client findClientByNumber(String number);

    Contract findContractByNumber(String number);

    List<Contract> findAllContracts();

    List<Contract> findFreeNumbers();

    Contract create(Contract t);

    Contract read(Long id);

    Contract update(Contract t);

    void delete(Contract t);

    List<Contract> getAll();
}
