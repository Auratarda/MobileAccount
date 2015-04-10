package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.entities.Tariff;
import com.tsystems.javaschool.service.TariffService;

import java.util.List;

/**
 * Created by Stanislav on 09.04.2015.
 */
public class TariffServiceImpl implements TariffService {
    @Override
    public void createNewTariff(String name, Long price) {

    }

    @Override
    public Tariff findTariffByName(String tariffName) {
        return null;
    }

    @Override
    public List<Tariff> findAllTariffs() {
        return null;
    }

    @Override
    public void addTariffOptions(String tariffName, String[] options) {

    }

    @Override
    public void removeOptionFromTariff(String optionName, String tariffName) {

    }

    @Override
    public void removeTariff(String tariffName) {

    }
}
