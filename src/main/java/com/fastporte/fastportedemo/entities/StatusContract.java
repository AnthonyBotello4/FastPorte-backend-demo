package com.fastporte.fastportedemo.entities;

import com.fastporte.fastportedemo.entities.enumeration.StatusContractEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "status_contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusContract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = true)
    private String status = StatusContractEnum.OFFER.name();

/*
    @OneToOne(mappedBy = "status")
    private Contract contract;
 */
}
