package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TARIFF")
public class Tariff {
    @Id
    @Column(name = "TARIFF_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tariffId;
    @Column(name = "TARIFF_NAME")
    private String name;
    @Column(name = "TARIFF_PRICE")
    private long price;
    @ManyToMany
    @JoinTable(name = "TARIFF_OPTIONS",
            joinColumns = @JoinColumn(name = "OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "TARIFF_ID"))
    private Set<Option> options = new HashSet<Option>(0);

    public Tariff() {
    }

    public Tariff(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
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

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "tariffId=" + tariffId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}