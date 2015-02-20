package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "contracts")
public class Contract implements Serializable{

    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contractId;
    @Column(name = "number")
    private String number;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "blocked_by_client")
    private Boolean blockedByClient;
    @Column(name = "blocked_by_operator")
    private Boolean blockedByOperator;
    @OneToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;
    @ManyToMany
    @JoinTable(name = "contract_options",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    private List<Option> options;

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
}