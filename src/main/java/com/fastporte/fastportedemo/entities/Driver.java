package com.fastporte.fastportedemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "photo")
    private String photo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 9)
    private String phone;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "type_of_user", nullable = false)
    private String typeOfUser;

    @Column(name = "photo_auto", nullable = false)
    private String photoAuto;


}
