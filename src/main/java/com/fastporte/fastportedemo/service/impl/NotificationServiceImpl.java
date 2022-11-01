package com.fastporte.fastportedemo.service.impl;

import com.fastporte.fastportedemo.entities.Notification;
import com.fastporte.fastportedemo.repository.INotificationRepository;
import com.fastporte.fastportedemo.service.INotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;

    public NotificationServiceImpl(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Long id) throws Exception {
        notificationRepository.deleteById(id);
    }

    @Override
    public Optional<Notification> getById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    /*
    @Override
    public Notification findByClient(Long id) {
        return notificationRepository.findByClient(id);
    }
     */
}

