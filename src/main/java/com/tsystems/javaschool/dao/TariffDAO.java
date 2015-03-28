package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Tariff;

import java.util.List;

/**
 * TariffDAO.
 */
public interface TariffDAO {
    Tariff findTariffByName(String tariffName);

    Tariff create(Tariff t);

    Tariff read(Long id);

    Tariff update(Tariff t);

    void delete(Tariff t);

    List<Tariff> getAll();
}
