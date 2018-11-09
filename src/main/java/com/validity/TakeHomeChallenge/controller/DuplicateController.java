package com.validity.TakeHomeChallenge.controller;
import com.validity.TakeHomeChallenge.model.Customer;
import com.validity.TakeHomeChallenge.service.CsvReaderService;
import com.validity.TakeHomeChallenge.service.DuplicateFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class DuplicateController {

    private CsvReaderService csvReaderService =
            CsvReaderService.getInstance();

    private DuplicateFinderService duplicateFinderService =
            DuplicateFinderService.getInstance();

    @RequestMapping(value="/duplicates",method = RequestMethod.GET)
    public ResponseEntity<?> getDuplicates(){

        List<Customer> customerList =
                csvReaderService.getCustomerList();
        List<Set<Customer>> duplicateList =
                duplicateFinderService.findDuplicates(customerList);

        return new ResponseEntity<>(duplicateList, HttpStatus.OK);
    }

    @RequestMapping(value="/unique", method = RequestMethod.GET)
    public ResponseEntity<?> getUnique(){

        List<Customer> customerList =
                csvReaderService.getCustomerList();
        List<Customer> unique =  duplicateFinderService.getUnique(customerList);

        return new ResponseEntity<>(unique, HttpStatus.OK);
    }
}
