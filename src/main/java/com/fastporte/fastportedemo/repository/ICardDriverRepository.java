package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.CardDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardDriverRepository extends JpaRepository<CardDriver, Long> {

}
