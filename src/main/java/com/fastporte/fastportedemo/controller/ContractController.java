package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.Contract;
import com.fastporte.fastportedemo.service.IClientService;
import com.fastporte.fastportedemo.service.IContractService;
import com.fastporte.fastportedemo.service.IDriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final IContractService contractService;
    private final IClientService clientService;
    private final IDriverService driverService;

    public ContractController(IContractService contractService, IClientService clientService, IDriverService driverService) {
        this.contractService = contractService;
        this.clientService = clientService;
        this.driverService = driverService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findAllContracts(){ //Response entity: la clase por defecto de spring para responder desde un controlador de API
        try {
            List<Contract> contracts = contractService.gettAll();

            if(contracts.size()>0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id){
        try {
            Optional<Contract> contract = contractService.getById(id);

            if(contract.isPresent()) //con isPresent se valida si es de tipo Contract o es nulo
                return new ResponseEntity<>(contract.get(), HttpStatus.OK); //se usa get porque es optional
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    public ResponseEntity<Contract> insertContract(Contract contract){
        try {
            Contract contractAux = contractService.save(contract);

            if(contractAux != null)
                return new ResponseEntity<>(contractAux, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */

}
