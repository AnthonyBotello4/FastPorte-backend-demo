package com.fastporte.fastportedemo.service.impl;

import com.fastporte.fastportedemo.entities.Driver;
import com.fastporte.fastportedemo.repository.IDriverRepository;
import com.fastporte.fastportedemo.service.IDriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class DriverServiceImpl implements IDriverService {

    private final IDriverRepository driverRepository;

    public DriverServiceImpl(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) throws Exception {
        return driverRepository.save(driver);
    }

    @Override
    public void delete(Long id) throws Exception {
        driverRepository.deleteById(id);
    }

    @Override
    public List<Driver> gettAll() throws Exception {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getById(Long id) throws Exception {
        return driverRepository.findById(id);
    }

    @Override
    public Driver findByEmailAndPassword(String email, String password) {
        return driverRepository.findByEmailAndPassword(email, password);
    }
}
