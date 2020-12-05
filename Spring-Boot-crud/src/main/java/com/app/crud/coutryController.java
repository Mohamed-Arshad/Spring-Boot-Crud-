/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/rest/v2")
public class coutryController {

    @Autowired
    private countryrep countryrep;

    //get all country
    @GetMapping("/country")
    public List<country> getAllCountry() {
        return countryrep.findAll();
    }

    //create country
    @PostMapping("/country")
    public country createCountry(@RequestBody country country) {
        return countryrep.save(country);
    }

    /*@GetMapping("/country/{id}")
    public ResponseEntity<country> getCountryById(@PathVariable(value ="id") long id){
       country country=countryrep.findById(id).orElseThrow();
       return ResponseEntity.ok().body(country);
    }*/
    //get by id
    @GetMapping("/country/{id}")
    public ResponseEntity<country> getCountryById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        country country = countryrep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NO country found on this id :: " + id));
        return ResponseEntity.ok().body(country);
    }
    
    //update by id
    @PutMapping("/country/{id}")
    public ResponseEntity<country> updatecountry(@PathVariable(value = "id") long id, @RequestBody country countryDetails) throws ResourceNotFoundException {
        country country = countryrep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No country found on this id :: " + id));

        country.setCountry(countryDetails.getCountry());
        country.setPopulation(countryDetails.getPopulation());
        final country updatedcountry = countryrep.save(country);
        return ResponseEntity.ok(updatedcountry);
    }

    //delete by id
    @DeleteMapping("/country/{id}")
    public Map<String, Boolean> deleteSubscriber(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        country country = countryrep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No country found on this id :: " + id));
        countryrep.delete(country);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
