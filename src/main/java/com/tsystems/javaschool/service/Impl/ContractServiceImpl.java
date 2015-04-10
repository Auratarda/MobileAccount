package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.dao.ContractDAO;
import com.tsystems.javaschool.domain.dao.OptionDAO;
import com.tsystems.javaschool.domain.dao.TariffDAO;
import com.tsystems.javaschool.domain.entities.Contract;
import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.domain.entities.Tariff;
import com.tsystems.javaschool.service.ContractService;
import com.tsystems.javaschool.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * This class contains business logic methods connected to the Contract entity.
 */
@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    @Qualifier("ContractDAOImpl")
    private ContractDAO contractDAO;
    @Autowired
    @Qualifier("TariffDAOImpl")
    private TariffDAO tariffDAO;
    @Autowired
    @Qualifier("OptionDAOImpl")
    private OptionDAO optionDAO;
    @Autowired
    @Qualifier("UtilServiceImpl")
    private UtilService utilService;

    /*
     * This method creates a new Contract without assigning it to a Client.
     **/
    @Override
    public void createNewContract(String number) {
        Contract contract = new Contract(number, false, false);
        contractDAO.create(contract);
    }

   /*
    * This method reassigns a Contract to another Tariff.
    **/
    @Override
    public List<Option> changeTariff(String contractNumber, String tariffName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        List<Option> contractOptions = contract.getOptions();
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        List<Option> tariffOptions = tariff.getOptions();
        List<Option> unacceptableOptions = new ArrayList<>(contractOptions);
        unacceptableOptions.removeAll(tariffOptions);
        if (unacceptableOptions.size() == 0) {
            contract.setTariff(tariff);
            contractDAO.update(contract);
        }
        return unacceptableOptions;
    }

    /*
     * This method adds Options to a Contract.
     **/
    @Override
    public List<Option> addOptionsToContract(String contractNumber, String[] optionNames) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        List<Option> contractOptions = contract.getOptions();
        List<Option> selectedOptions = utilService.unzipOptions(optionNames);
        List<Option> optionsToAdd = new ArrayList<>();
        /*Here all the Required options are added. **/
        for (Option option : selectedOptions) {
            optionsToAdd.add(option);
            optionsToAdd.addAll(option.getRequiredOptions());
        }
        /*Here all the Incompatible options are gathered.
          A HashSet is used to avoid duplicates. **/
        Set<Option> incOptions = new HashSet<>();
        for (Option option : optionsToAdd) {
            incOptions.addAll(option.getIncompatibleOptions());
        }
        for (Option contractOption : contractOptions) {
            incOptions.addAll(contractOption.getIncompatibleOptions());
        }
        /*Here the Set is converted to a List. **/
        List<Option> incompatibleOptions = new ArrayList<>();
        for (Object option : incOptions) {
            incompatibleOptions.add((Option) option);
        }
        List<Option> result = new ArrayList<Option>(optionsToAdd);
        /*Here the Contract Options are added to the Selected Options. **/
        result.addAll(contractOptions);
        /*Here we find out if the Selected Options are incompatible
         to the Contract Options or to each other. **/
        result.retainAll(incompatibleOptions);

        if (result.size() == 0){
            for (Option option : optionsToAdd) {
                contract.getOptions().add(option);
            }
            contractDAO.update(contract);
        }
        return result;
    }

    /*
     * This method removes an Option from a Contract.
     * If ony of the Contract Options require this Option,
      * the Client would be notified that another Option should be removed first.
     **/
    @Override
    public List<Option> removeOptionFromContract(String contractNumber, String optionName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        List<Option> contractOptions = contract.getOptions();
        /*Here the Required Options of the Contract Options are added. **/
        Set<Option> reqOptions = new HashSet<>();
        for (Option option : contractOptions) {
            if (!option.getName().equals(optionName))reqOptions.addAll(option.getRequiredOptions());
        }
        List<Option> requiredOptions = new ArrayList<>();
        for (Option reqOption : reqOptions) {
            if (reqOption.getName().equals(optionName)) requiredOptions.add(reqOption);
        }
        /*Here it is checked that there remains no Contract Options
        that require the Option we want to remove. **/
        if(requiredOptions.size()==0){
            Option option = optionDAO.findOptionByName(optionName);
            Iterator<Option> it = contract.getOptions().iterator();
            while (it.hasNext()){
                Option opt = it.next();
                if(opt.getOptionId() == option.getOptionId()){
                    break;
                }
            }
            it.remove();
            contractDAO.update(contract);
        }
        return requiredOptions;
    }

    /*
     * This method locks a Contract by Client.
     * The client could unlock it without the help of operator.
     * No changes could be made by the Client while the Contract is locked.
     **/
    @Override
    public void lockContractByClient(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByClient(true);
        contractDAO.update(contract);
    }

    /*
     * This method unlocks a Contract by Client.
     **/
    @Override
    public void unlockContractByClient(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByClient(false);
        contractDAO.update(contract);
    }

    /*
     * This method locks a Contract by Operator.
     * The client couldn't unlock it without the help of operator.
     * No changes could be made by the Client while the Contract is locked.
     **/
    @Override
    public void lockContractByOperator(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByOperator(true);
        contractDAO.update(contract);
    }

    /*
    * This method unlocks a Contract by Operator.
    **/
    @Override
    public void unlockContractByOperator(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByOperator(false);
        contractDAO.update(contract);
    }

    /*
    * This method retrieves Contract by number.
    **/
    @Override
    public Contract findContractByNumber(String contractNumber) {
        return contractDAO.findContractByNumber(contractNumber);
    }

    /*
    * This method retrieves all Contracts assigned to Clients.
    **/
    @Override
    public List<Contract> findAllContracts() {
        return contractDAO.findAllContracts();
    }

    /*
   * This method retrieves all Contracts not assigned to Clients.
   **/
    @Override
    public List<Contract> findFreeContracts() {
        return contractDAO.findFreeNumbers();
    }

    @Override
    public List<Contract> getInfo(String tariffName) {
        return null;
    }
}
