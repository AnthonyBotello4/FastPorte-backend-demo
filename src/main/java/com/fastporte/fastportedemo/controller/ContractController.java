package com.fastporte.fastportedemo.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastporte.fastportedemo.entities.Client;
import com.fastporte.fastportedemo.entities.Contract;
import com.fastporte.fastportedemo.entities.Driver;
import com.fastporte.fastportedemo.service.IClientService;
import com.fastporte.fastportedemo.service.IContractService;
import com.fastporte.fastportedemo.service.IDriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import java.util.Spliterator;

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

    //Retornar todos los contratos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findAllContracts() { //Response entity: la clase por defecto de spring para responder desde un controlador de API
        try {
            List<Contract> contracts = contractService.gettAll();

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar contrato por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id) {
        try {
            Optional<Contract> contract = contractService.getById(id);

            if (contract.isPresent()) //con isPresent se valida si es de tipo Contract o es nulo
                return new ResponseEntity<>(contract.get(), HttpStatus.OK); //se usa get porque es optional
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por driver con status OFFER
    @GetMapping(value = "/offer/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findContractByStatusOffer( @PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.gettAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("OFFER"));
            contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por user(client/driver) con status PENDING
    @GetMapping(value = "/pending/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findContractByStatusPending(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.gettAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("PENDING"));
            if(user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            //Quitar el campo Driver de los contratos
            //contracts.forEach(contract -> contract.setDriver(null));

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por user (client/driver) con status HISTORY
    @GetMapping(value = "/history/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findContractByStatusHistory(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.gettAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("HISTORY"));
            if(user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            //Quitar el campo Driver de los contratos
            //contracts.forEach(contract -> contract.setDriver(null));

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Crear un contrato poe driver y cliente
    @PostMapping(value = "/add/client={clientId}/driver={driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> insertContract(@PathVariable("clientId") Long clientId,
                                                   @PathVariable("driverId") Long driverId,
                                                   @Valid @RequestBody Contract contract) {
        try {
            Optional<Client> client = clientService.getById(clientId);
            Optional<Driver> driver = driverService.getById(driverId);

            if (client.isPresent() && driver.isPresent()) {
                contract.setClient(client.get());
                contract.setDriver(driver.get());
                Contract contractNew = contractService.save(contract);
                return ResponseEntity.status(HttpStatus.CREATED).body(contractNew);
            } else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
