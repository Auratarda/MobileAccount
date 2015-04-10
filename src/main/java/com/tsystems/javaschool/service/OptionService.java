package com.tsystems.javaschool.service;

import com.tsystems.javaschool.domain.entities.Option;

import java.util.List;

/**
 * Created by Stanislav on 09.04.2015.
 */
public interface OptionService {
    void createNewOption(String name, Long optionPrice, Long connectionCost);

    Option findOptionByName(String optionName);

    List<Option> findOptions(String [] optionsNames);

    List<Option> checkTariffCompatibility(String selectedTariff, String [] optionsNames);

    List<Option> checkOptionsCompatibility(String [] optionsNames);

    List<Option> findAllOptions();

    List<Option> findOptionsByTariff(String tariffName);

    void removeOption(String optionName);

    void addRequiredOptions(String currentOption, String[] requiredOptions);

    void addIncompatibleOption(String currentOption, String incompatibleOptions);

    void removeRequiredOption(String currentOption, String optionToRemove);

    void removeIncompatibleOption(String currentOption, String optionToRemove);

    List<Option> getRequiredOptions (String optionName);

    List<Option> getIncompatibleOptions(String optionName);

}
