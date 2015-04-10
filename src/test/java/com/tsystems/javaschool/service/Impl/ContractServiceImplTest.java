package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.dao.ContractDAO;
import com.tsystems.javaschool.domain.dao.TariffDAO;
import com.tsystems.javaschool.domain.entities.Contract;
import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.domain.entities.Tariff;
import com.tsystems.javaschool.service.UtilService;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ContractServiceImplTest {
    @InjectMocks
    ContractServiceImpl contractService;
    @Mock
    private ContractDAO mockContractDAO;
    @Mock
    private TariffDAO mockTariffDAO;
    @Mock
    private UtilService mockUtilService;

    @Before
    public void init() throws ClientNotFoundException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testChangeTariff() throws Exception {
        String contractNumber = "9042222222";
        String tariffName = "Желтый";

        Contract contract = new Contract();
        List<Option> contractOptions = new ArrayList<>();
        Option firstOption = new Option();
        firstOption.setName("Опция 1");
        contractOptions.add(firstOption);
        contract.setOptions(contractOptions);

        Tariff tariff = new Tariff();
        List<Option> tariffOptions = new ArrayList<>();
        tariff.setOptions(tariffOptions);

        Mockito.when(mockContractDAO.findContractByNumber(contractNumber)).thenReturn(contract);
        Mockito.when(mockTariffDAO.findTariffByName(tariffName)).thenReturn(tariff);

        List<Option> unacceptableOptions = contractService.changeTariff(contractNumber, tariffName);

        Assert.assertEquals(unacceptableOptions.size(), 1);

        tariffOptions.add(firstOption);
        unacceptableOptions = contractService.changeTariff(contractNumber, tariffName);

        Assert.assertEquals(0, unacceptableOptions.size());
    }

    @Test
    public void testAddOptionsToContract() throws Exception {
        String contractNumber = "9042222222";
        String[] optionNames = {"Опция 1", "Опция 2", "Опция 3"};
        List<Option> options = new ArrayList<>();
        Option firstOption = new Option();
        firstOption.setName("Опция 1");
        Option secondOption = new Option();
        secondOption.setName("Опция 2");
        Option thirdOption = new Option();
        thirdOption.setName("Опция 3");
        firstOption.getIncompatibleOptions().add(secondOption);
        secondOption.getIncompatibleOptions().add(firstOption);
        options.add(firstOption);
        options.add(secondOption);
        options.add(thirdOption);

        Contract contract = new Contract();
        Mockito.when(mockContractDAO.findContractByNumber(contractNumber)).thenReturn(contract);

        Mockito.when(mockUtilService.unzipOptions(optionNames)).thenReturn(options);

        List<Option> result = contractService.addOptionsToContract(contractNumber, optionNames);

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testRemoveOptionFromContract() throws Exception {
        String contractNumber = "9042222222";
        String optionName = "Опция 1";

        Contract contract = new Contract();
        Option firstOption = new Option();
        firstOption.setName("Опция 1");
        Option secondOption = new Option();
        secondOption.setName("Опция 2");
        secondOption.getRequiredOptions().add(firstOption);
        contract.getOptions().add(firstOption);
        contract.getOptions().add(secondOption);

        Mockito.when(mockContractDAO.findContractByNumber(contractNumber)).thenReturn(contract);

        List<Option> result = contractService.removeOptionFromContract(contractNumber, optionName);

        Assert.assertEquals(1, result.size());
    }
}