package com.tsystems.javaschool.service.Impl;

import com.tsystems.javaschool.domain.dao.OptionDAO;
import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 10.04.2015.
 */
@Repository("UtilServiceImpl")
public class UtilServiceImpl implements UtilService {

    @Autowired
    @Qualifier("OptionDAOImpl")
    private OptionDAO optionDAO;

    @Override
    public List<Option> unzipOptions(String[] optionNames) {
        List<Option> options = new ArrayList<Option>(0);
        for (String optionName : optionNames) {
            Option option = optionDAO.findOptionByName(optionName);
            options.add(option);
        }
        return options;
    }
}
