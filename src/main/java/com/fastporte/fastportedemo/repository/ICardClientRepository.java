package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.CardClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardClientRepository extends JpaRepository<CardClient, Long> {

}

