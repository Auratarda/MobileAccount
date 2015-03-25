package com.tsystems.javaschool.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContractDTO implements Serializable {

    private String number;
    private ClientDTO client;
    private Boolean blockedByClient;
    private Boolean blockedByOperator;
    private TariffDTO tariff;
    private List<OptionDTO> options = new ArrayList<OptionDTO>(0);

    public ContractDTO() {
    }

    public ContractDTO(String number, ClientDTO client, Boolean blockedByClient,
                       Boolean blockedByOperator, TariffDTO tariff, List<OptionDTO> options) {
        this.number = number;
        this.client = client;
        this.blockedByClient = blockedByClient;
        this.blockedByOperator = blockedByOperator;
        this.tariff = tariff;
        this.options = options;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Boolean getBlockedByClient() {
        return blockedByClient;
    }

    public void setBlockedByClient(Boolean blocked) {
        this.blockedByClient = blocked;
    }

    public Boolean getBlockedByOperator() {
        return blockedByOperator;
    }

    public void setBlockedByOperator(Boolean blockedByOperator) {
        this.blockedByOperator = blockedByOperator;
    }

    public TariffDTO getTariff() {
        return tariff;
    }

    public void setTariff(TariffDTO tariff) {
        this.tariff = tariff;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "client: " + client +
                ", number='" + number + '\'' +
                '}';
    }
}