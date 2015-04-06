package com.tsystems.javaschool.facade.controllers;

import com.tsystems.javaschool.facade.dto.InfoDTO;
import com.tsystems.javaschool.service.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * WebServiceController.
 */
@RestController
@RequestMapping("/info")
public class WebServiceController {

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;

    @RequestMapping(value = "/clientsOnTariff", method = RequestMethod.GET)
    public ResponseEntity<List<InfoDTO>> getInfo(@RequestParam String tariffName){
        if (tariffName.equals("green")) tariffName = "Зеленый";
        List<InfoDTO> infoDTOs = operatorService.getInfo(tariffName);
        return new ResponseEntity<>(infoDTOs, HttpStatus.OK);
    }

}
