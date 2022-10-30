package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.Contract;
import com.fastporte.fastportedemo.entities.Driver;
import com.fastporte.fastportedemo.service.IDriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final IDriverService driverService;

    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    //Retornar driver por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> findDriverById(@PathVariable("id") Long id) {
        try {
            Optional<Driver> driver = driverService.getById(id);
            if (driver.isPresent())
                return new ResponseEntity<>(driver.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar driver por email y password
    @GetMapping(value = "/email={email}/password={password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> findDriverByEmailAndPassword(@PathVariable("email") String email,
                                                               @PathVariable("password") String password) {
        try {
            Driver driver = driverService.findByEmailAndPassword(email, password);
            if (driver!=null)
                return new ResponseEntity<>(driver, HttpStatus.OK);
                // return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
