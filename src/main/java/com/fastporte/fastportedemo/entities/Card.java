package com.fastporte.fastportedemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @Column(name = "card_nickname", nullable = false)
    private String cardNickname;

    @Column(name = "card_number", nullable = false, length = 16)
    private int cardNumber;

    @Column(name = "expiration_date", nullable = false, length = 5)
    private Date expirationDate;

    @Column(name = "issuer", nullable = false)
    private String issuer;

    @Column(name = "zip", nullable = false, length = 5)
    private int zip;

    @Column(name = "email", nullable = false)
    private String email;

    /*
    @OneToOne(mappedBy = "card")
    private CardDriver cardDriver;

    @OneToOne(mappedBy = "card")
    private CardClient cardClient;
     */

}
