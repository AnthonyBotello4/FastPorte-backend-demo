package com.fastporte.fastportedemo.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Column(name = "client_id", nullable = false)
    private Long idClient;

    @Column(name = "driver_id", nullable = false)
    private Long idDriver;
     */

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "quantity", nullable = false)
    private String quantity;


    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date contractDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Driver driver;
}