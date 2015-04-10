package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.service.OptionService;

import java.util.List;

/**
 * Created by Stanislav on 09.04.2015.
 */
public class OptionServiceImpl implements OptionService {
    @Override
    public void createNewOption(String name, Long optionPrice, Long connectionCost) {

    }

    @Override
    public Option findOptionByName(String optionName) {
        return null;
    }

    @Override
    public List<Option> findOptions(String[] optionsNames) {
        return null;
    }

    @Override
    public List<Option> checkTariffCompatibility(String selectedTariff, String[] optionsNames) {
        return null;
    }

    @Override
    public List<Option> checkOptionsCompatibility(String[] optionsNames) {
        return null;
    }

    @Override
    public List<Option> findAllOptions() {
        return null;
    }

    @Override
    public List<Option> findOptionsByTariff(String tariffName) {
        return null;
    }

    @Override
    public void removeOption(String optionName) {

    }

    @Override
    public void addRequiredOptions(String currentOption, String[] requiredOptions) {

    }

    @Override
    public void addIncompatibleOption(String currentOption, String incompatibleOptions) {

    }

    @Override
    public void removeRequiredOption(String currentOption, String optionToRemove) {

    }

    @Override
    public void removeIncompatibleOption(String currentOption, String optionToRemove) {

    }

    @Override
    public List<Option> getRequiredOptions(String optionName) {
        return null;
    }

    @Override
    public List<Option> getIncompatibleOptions(String optionName) {
        return null;
    }
}
