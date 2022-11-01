package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {

}
