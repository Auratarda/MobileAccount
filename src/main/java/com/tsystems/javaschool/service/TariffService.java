package com.tsystems.javaschool.service;

import com.tsystems.javaschool.domain.entities.Tariff;

import java.util.List;

/**
 * Created by Stanislav on 09.04.2015.
 */
public interface TariffService {
    void createNewTariff(String name, Long price);

    Tariff findTariffByName(String tariffName);

    List<Tariff> findAllTariffs();

    void addTariffOptions(String tariffName, String[] options);

    void removeOptionFromTariff(String optionName, String tariffName);

    void removeTariff(String tariffName);
}
