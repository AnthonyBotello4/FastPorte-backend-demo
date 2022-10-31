package com.fastporte.fastportedemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import com.fastporte.fastportedemo.entities.Client;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private Long id;

    @Column(name = "read_status", nullable = false)
    private Boolean readStatus = false;

    //Unir columnas contract y notification para obtener los datos del cliente
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "contract_id")
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //private Contract contract;

    //Unir columnas contract y notification para obtener los datos del cliente
    /*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Contract contract;
     */


}
