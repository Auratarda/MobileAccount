package com.tsystems.javaschool.dto;

import java.util.ArrayList;
import java.util.List;

public class OptionDTO {

    private String name;
    private long price;
    private long connectionCost;
    private List<OptionDTO> requiredOptions = new ArrayList<OptionDTO>(0);
    private List<OptionDTO> incompatibleOptions = new ArrayList<OptionDTO>(0);

    public OptionDTO() {
    }

    public OptionDTO(String name, long price, long connectionCost) {
        this.name = name;
        this.price = price;
        this.connectionCost = connectionCost;
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

    public List<OptionDTO> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(List<OptionDTO> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public List<OptionDTO> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(List<OptionDTO> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", optionPrice=" + price +
                ", connectionCost=" + connectionCost +
                '}';
    }
}