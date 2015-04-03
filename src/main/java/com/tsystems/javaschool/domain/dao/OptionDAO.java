package com.tsystems.javaschool.domain.dao;

import com.tsystems.javaschool.domain.entities.Option;

/**
 * OptionDAO.
 */
public interface OptionDAO extends GenericDao<Option>{

    Option findOptionByName(String optionName);
}
