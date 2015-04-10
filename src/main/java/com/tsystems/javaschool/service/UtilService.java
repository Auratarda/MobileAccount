package com.tsystems.javaschool.service;

import com.tsystems.javaschool.domain.entities.Option;

import java.util.List;

/**
 * Created by Stanislav on 10.04.2015.
 */
public interface UtilService {
    List<Option> unzipOptions(String[] optionNames);
}
