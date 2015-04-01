package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Tariff;

/**
 * TariffDAO.
 */
public interface TariffDAO extends GenericDao<Tariff>{
    Tariff findTariffByName(String tariffName);
}
