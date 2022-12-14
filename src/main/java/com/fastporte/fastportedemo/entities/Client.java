package com.fastporte.fastportedemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "photo", nullable = true)
    private String photo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 9)
    private String phone;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type_of_user", nullable = false)
    private String typeOfUser;

    //Unir columnas para obtener sus notificaciones
    // @OneToOne(mappedBy = "client")
    // private Notification notification;

    //Unir columnas para obtener sus contratos


}
