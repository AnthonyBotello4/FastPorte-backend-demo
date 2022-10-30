package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.Client;
import com.fastporte.fastportedemo.service.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    //Retornar client por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientById(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getById(id);
            if (client.isPresent())
                return new ResponseEntity<>(client.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar client por email y password
    @GetMapping(value = "/email={email}/password={password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientByEmailAndPassword(@PathVariable("email") String email,
                                                               @PathVariable("password") String password) {
        try {
            Client client = clientService.findByEmailAndPassword(email, password);
            if (client!=null)
                return new ResponseEntity<>(client, HttpStatus.OK);
                // return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
