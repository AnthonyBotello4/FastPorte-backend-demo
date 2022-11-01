package com.fastporte.fastportedemo.controller;

import com.fastporte.fastportedemo.entities.Card;
import com.fastporte.fastportedemo.entities.CardClient;
import com.fastporte.fastportedemo.entities.Client;
import com.fastporte.fastportedemo.service.ICardClientService;
import com.fastporte.fastportedemo.service.ICardService;
import com.fastporte.fastportedemo.service.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cardsClient")
public class CardClientController {
    private final ICardClientService cardClientService;
    private final IClientService clientService;
    private final ICardService cardService;

    public CardClientController(ICardClientService cardClientService, IClientService clientService, ICardService cardService) {
        this.cardClientService = cardClientService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    //Retornar todos los cardsClient
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<CardClient>> findAll() {
        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            if (cardsClient.size() > 0)
                return new ResponseEntity<>(cardsClient, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener los cards de un client por id
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<CardClient>> getCardsByClientId(@PathVariable("id") Long id) {

        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            cardsClient.removeIf(cardClient -> !cardClient.getClient().getId().equals(id));
            /*
            List<Card> cards = null;

            for (CardClient cardClient : cardsClient) {
                cards.add(cardClient.getCard());
            }
             */
            if (cardsClient.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cardsClient, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Agregar un card a un client
    @PostMapping(value = "/{idClient}/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardClient> addCardToClient(@PathVariable("idClient") Long idClient,
                                                      @Valid @RequestBody Card card) {
        try {
            Optional<Client> client = clientService.getById(idClient);
            if (client.isPresent()) {
                Long id = cardService.save(card).getId();
                System.out.println("Id: " + id);
                CardClient cardClient = new CardClient();
                cardClient.setClient(client.get());
                cardClient.setCard(cardService.getById(id).get());
                cardClientService.save(cardClient);
                id = 0L;
                return new ResponseEntity<>(cardClient, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Eliminar un card de un client
    @DeleteMapping(value = "/{idClient}/delete/{idCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteCardFromClient(@PathVariable("idClient") Long idClient,
                                                           @PathVariable("idCard") Long idCard) {
        try {
            Optional<Client> client = clientService.getById(idClient);
            Optional<Card> card = cardService.getById(idCard);

            if (client.isPresent() && card.isPresent()) {
                List<CardClient> cardsClient = cardClientService.getAll();
                cardsClient.removeIf(cardClient ->
                        !(cardClient.getClient().getId().equals(idClient) &&
                                cardClient.getCard().getId().equals(idCard)));

                /*
                for (CardClient cardClient : cardsClient) {
                    System.out.println("CardClient: " + cardClient.getId());
                }
                 */
                cardClientService.delete(cardsClient.get(0).getId());
                cardService.delete(idCard);

                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
