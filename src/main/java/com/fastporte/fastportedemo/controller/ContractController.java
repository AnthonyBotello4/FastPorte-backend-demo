package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.*;
import com.fastporte.fastportedemo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final IContractService contractService;
    private final IClientService clientService;
    private final IDriverService driverService;
    private final IStatusContractService statusContractService;
    private final INotificationService notificationService;

    public ContractController(IContractService contractService, IClientService clientService, IDriverService driverService, IStatusContractService statusContractService, INotificationService notificationService) {
        this.contractService = contractService;
        this.clientService = clientService;
        this.driverService = driverService;
        this.statusContractService = statusContractService;
        this.notificationService = notificationService;
    }

    //Retornar todos los contratos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findAllContracts() { //Response entity: la clase por defecto de spring para responder desde un controlador de API
        try {
            List<Contract> contracts = contractService.getAll();

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
            List<Contract> contracts = contractService.getAll();
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

    //Retornar los contratos por user(client/river) con status OFFER
    @GetMapping(value = "/offer/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findContractByStatusOfferUser( @PathVariable("user") String user, @PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("OFFER"));
            if(user.equals("client")){
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            }else if(user.equals("driver")){
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
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
            List<Contract> contracts = contractService.getAll();
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
            List<Contract> contracts = contractService.getAll();
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

    //Crear un contrato por driver y cliente
    @PostMapping(value = "/add/client={clientId}/driver={driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contract> insertContract(@PathVariable("clientId") Long clientId,
                                                   @PathVariable("driverId") Long driverId,
                                                   @Valid @RequestBody Contract contract) {
        try {
            Optional<Client> client = clientService.getById(clientId);
            Optional<Driver> driver = driverService.getById(driverId);
            List<Contract> contracts = contractService.getAll();
            System.out.println(contracts.get(contracts.size()-1).getId());
            if (client.isPresent() && driver.isPresent()) {

                contract.setClient(client.get());
                contract.setDriver(driver.get());
                contract.setStatus(statusContractService.getById(1L).get());
                contract.setNotification(notificationService.getById(0L).get());
                System.out.println(contract);
                Contract contractNew = contractService.save(contract);
                return ResponseEntity.status(HttpStatus.CREATED).body(contractNew);
            } else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener las notificaciones de un client
    @GetMapping(value = "/notifications-client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findNotificationsByClient(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            /*
            contracts.forEach(contract -> {
                    contract.setDriverTmp(contract.getDriver());
            });
             */

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obetner las notificaciones no leídas de un client
    @GetMapping(value = "/unread-notifications/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findNotificationsUnreadByUser(@PathVariable("id") Long id,
                                                                          @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            if(user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            }

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener las notificaciones de un driver
    @GetMapping(value = "/notifications-driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contract>> findNotificationsByDriver(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            /*
            contracts.forEach(contract -> {
                contract.setClientTmp(contract.getClient());
            });
             */

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Cambiar el status del contrato de OFFER a PENDING - aquí se añade al driver al contrato
    @PutMapping(value = "/{idContract}/change-status-offer-to-pending/driver={idDriver}")
    public ResponseEntity<Contract> updateContractStatusOfferToPending(@PathVariable("idContract") Long idContract,
                                                                       @PathVariable("idDriver") Long idDriver) {
        try {

            Optional<Contract> contract = contractService.getById(idContract);
            Optional<StatusContract> statusContract = statusContractService.getById(2L);

            if(contract.isPresent() && statusContract.isPresent()){
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);
                contractUpdate.setStatus(statusContract.get());
                contractUpdate.setDriver(driverService.getById(idDriver).get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar el status de un contrato (solo de pending a history)
    @PutMapping(value = "/{idContract}/update-status/{idContractStatus}")
    public ResponseEntity<Contract> updateContractStatusPatch(@PathVariable("idContract") Long idContract,
                                                         @PathVariable("idContractStatus") Long idContractStatus) {
        try {

            if(idContractStatus <= 1 || idContractStatus > 3){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Contract> contract = contractService.getById(idContract);
            Optional<StatusContract> statusContract = statusContractService.getById(idContractStatus);

            if(contract.isPresent() && statusContract.isPresent()){
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);
                contractUpdate.setStatus(statusContract.get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Intercalar el status de una notificacion (de leida a no leida o viceversa)
    @PutMapping(value = "/{idContract}/change-notification-status")
    public ResponseEntity<Contract> updateContractNotificationPatch(@PathVariable("idContract") Long idContract) {
        try {

            Optional<Contract> contract = contractService.getById(idContract);

            if(contract.isPresent()){

                Optional<Notification> notification;
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);

                if(contractUpdate.getNotification().getId() == 0){
                    notification = notificationService.getById(1L);
                } else {
                    notification = notificationService.getById(0L);
                }

                contractUpdate.setNotification(notification.get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Cambiar el status de las notificaciones no leídas a leídas
    @PutMapping(value = "/change-notification-status")
    public ResponseEntity<Contract> updateContractNotificationPatch() {
        try {

            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> contract.getNotification().getId() == 1L);
            Optional<Notification> notification = notificationService.getById(1L);

            if(contracts.size() > 0){
                contracts.forEach(contract -> {
                    contract.setNotification(notification.get());
                    try {
                        contractService.save(contract);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
