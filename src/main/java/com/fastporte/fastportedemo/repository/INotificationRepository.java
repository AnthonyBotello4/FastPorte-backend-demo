package com.fastporte.fastportedemo.repository;

import com.fastporte.fastportedemo.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    //Notification findByClient(Long id);

}
