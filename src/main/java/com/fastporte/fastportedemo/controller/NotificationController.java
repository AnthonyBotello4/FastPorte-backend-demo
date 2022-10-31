package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.Notification;
import com.fastporte.fastportedemo.service.INotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }


    /*
    @GetMapping(value = "/contract/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> findNotificationByClientId(@PathVariable("id") Long id) {
        try {
            Notification notification = notificationService.findByClient(id);
            if (notification!=null)
                return new ResponseEntity<>(notification, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */

    //Obtener las notificaciones de un cliente
    /*
    @GetMapping(value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> findNotificationByContractId(@PathVariable("id") Long id) {
        try {
            Notification notification = notificationService.findByClient(id);
            if (notification!=null)
                return new ResponseEntity<>(notification, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */

}
