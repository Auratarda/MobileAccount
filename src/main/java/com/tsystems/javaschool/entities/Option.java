package com.tsystems.javaschool.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long optionId;
    @Column(name = "option_name")
    private String name;
    @Column(name = "option_price")
    private BigDecimal optionPrice;
    @Column(name = "connection_cost")
    private BigDecimal connectionCost;

    @ManyToMany
    @JoinTable(name = "incompatible_options",
            joinColumns = @JoinColumn(name = "inc_option_id"),
            inverseJoinColumns = @JoinColumn(name = "current_option_id"))
    private List<Option> incompatibleOptions;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "required_options",
            joinColumns = @JoinColumn(name = "req_option_id"),
            inverseJoinColumns = @JoinColumn(name = "current_option_id"))
    private Set<Option> requiredOptions;

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

    public BigDecimal getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(BigDecimal optionPrice) {
        this.optionPrice = optionPrice;
    }

    public BigDecimal getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(BigDecimal connectionCost) {
        this.connectionCost = connectionCost;
    }

    public Set<Option> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(Set<Option> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

//    public List<Option> getIncompatibleOptions() {
//        return incompatibleOptions;
//    }
//
//    public void setIncompatibleOptions(List<Option> incompatibleOptions) {
//        this.incompatibleOptions = incompatibleOptions;
//    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", optionPrice=" + optionPrice +
                '}';
    }
}

