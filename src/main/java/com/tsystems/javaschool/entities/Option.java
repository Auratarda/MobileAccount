package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OPTIONS")
public class Option {

    @Id
    @Column(name = "OPTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long optionId;
    @Column(name = "OPTION_NAME")
    private String name;
    @Column(name = "OPTION_PRICE")
    private long optionPrice;
    @Column(name = "CONNECTION_COST")
    private long connectionCost;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "REQ_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CURRENT_OPTION_ID"))
    private Set<Option> requiredOptions = new HashSet<Option>(0);

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "INC_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CURRENT_OPTION_ID"))
    private Set<Option> incompatibleOptions = new HashSet<Option>(0);

    public Option() {
    }

    public Option(String name, long optionPrice, long connectionCost) {
        this.name = name;
        this.optionPrice = optionPrice;
        this.connectionCost = connectionCost;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(long optionPrice) {
        this.optionPrice = optionPrice;
    }

    public long getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(long connectionCost) {
        this.connectionCost = connectionCost;
    }

    public Set<Option> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(Set<Option> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", name='" + name + '\'' +
                ", optionPrice=" + optionPrice +
                ", connectionCost=" + connectionCost +
                '}';
    }
}