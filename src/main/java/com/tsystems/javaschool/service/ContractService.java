package com.tsystems.javaschool.service;

import com.tsystems.javaschool.domain.entities.Contract;
import com.tsystems.javaschool.domain.entities.Option;

import java.util.List;

/**
 * Created by Stanislav on 09.04.2015.
 */
public interface ContractService {
    void createNewContract(String number);

    List<Option> changeTariff(String contractNumber, String tariffName);

    List<Option> addOptionsToContract(String contractNumber, String [] optionNames);

    List<Option> removeOptionFromContract(String contractNumber, String optionName);

    void lockContractByClient(String contractNumber);

    void unlockContractByClient(String contractNumber);

    void lockContractByOperator(String contractNumber);

    void unlockContractByOperator(String contractNumber);

    List<Contract> getInfo(String tariffName);

    Contract findContractByNumber(String contractNumber);

    List<Contract> findAllContracts();

    List<Contract> findFreeContracts();
}
