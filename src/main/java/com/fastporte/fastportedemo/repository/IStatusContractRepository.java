package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.StatusContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusContractRepository extends JpaRepository<StatusContract, Long> {


}
