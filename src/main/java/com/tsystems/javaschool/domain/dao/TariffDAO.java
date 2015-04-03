package com.tsystems.javaschool.domain.dao;

import com.tsystems.javaschool.domain.entities.Tariff;

/**
 * TariffDAO.
 */
public interface TariffDAO extends GenericDao<Tariff>{
    Tariff findTariffByName(String tariffName);
}
