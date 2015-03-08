package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTRACT")
public class Contract implements Serializable{

    @Id
    @Column(name = "CONTRACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contractId;
    @Column(name = "CONTRACT_NUMBER")
    private String number;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
    @Column(name = "BLOCKED_BY_CLIENT")
    private boolean blockedByClient;
    @Column(name = "BLOCKED_BY_OPERATOR")
    private boolean blockedByOperator;
    @OneToOne
    @JoinColumn(name = "TARIFF_ID")
    private Tariff tariff;
    @ManyToMany
    @JoinTable(name = "CONTRACT_OPTIONS",
            joinColumns = @JoinColumn(name = "OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_ID"))
    private List<Option> options = new ArrayList<Option>(0);

    public Contract() {
    }

    public Contract(String number, Boolean blockedByClient, Boolean blockedByOperator) {
        this.number = number;
        this.blockedByClient = blockedByClient;
        this.blockedByOperator = blockedByOperator;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
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

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", number='" + number + '\'' +
                '}';
    }
}