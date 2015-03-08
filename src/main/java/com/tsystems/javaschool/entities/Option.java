package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private long price;
    @Column(name = "CONNECTION_COST")
    private long connectionCost;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "REQUIRED_OPTIONS",
            joinColumns = @JoinColumn(name = "REQ_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CURRENT_OPTION_ID"))
    private List<Option> requiredOptions = new ArrayList<Option>(0);

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "INCOMPATIBLE_OPTIONS",
            joinColumns = @JoinColumn(name = "INC_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CURRENT_OPTION_ID"))
    private List<Option> incompatibleOptions = new ArrayList<Option>(0);

    public Option() {
    }

    public Option(String name, long price, long connectionCost) {
        this.name = name;
        this.price = price;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long optionPrice) {
        this.price = optionPrice;
    }

    public long getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(long connectionCost) {
        this.connectionCost = connectionCost;
    }

    public List<Option> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(List<Option> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public List<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(List<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", name='" + name + '\'' +
                ", optionPrice=" + price +
                ", connectionCost=" + connectionCost +
                '}';
    }
}