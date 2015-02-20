package com.tsystems.javaschool.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "currentOption",
            fetch=FetchType.EAGER,
            cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Option> requiredOptions = new ArrayList<Option>(0);
    @OneToMany(mappedBy = "currentOption",
            fetch=FetchType.EAGER,
            cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Option> incompatibleOptions = new ArrayList<Option>(0);
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="option_id", referencedColumnName = "option_id", insertable = false, updatable = false)
    private Option currentOption;

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
}

