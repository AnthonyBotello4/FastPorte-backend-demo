package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Client findByEmailAndPassword(String email, String password);

}
