package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Option;

import java.util.List;

/**
 * OptionDAO.
 */
public interface OptionDAO {
    Option findOptionByName(String optionName);

    Option create(Option t);

    Option read(Long id);

    Option update(Option t);

    void delete(Option t);

    List<Option> getAll();
}
