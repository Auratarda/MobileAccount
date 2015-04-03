package com.tsystems.javaschool.facade.dto;

public class TariffDTO {
    private String name;
    private long price;

    public TariffDTO() {
    }

    public TariffDTO(String name, long price) {
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}