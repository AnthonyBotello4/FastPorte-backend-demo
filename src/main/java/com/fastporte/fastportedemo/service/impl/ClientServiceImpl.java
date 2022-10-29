package com.fastporte.fastportedemo.service.impl;

import com.fastporte.fastportedemo.entities.Client;
import com.fastporte.fastportedemo.repository.IClientRepository;
import com.fastporte.fastportedemo.service.IClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Client save(Client client) throws Exception {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> gettAll() throws Exception {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) throws Exception {
        return clientRepository.findById(id);
    }

    @Override
    public Client findByEmailAndPassword(String email, String password) throws Exception {
        return clientRepository.findByEmailAndPassword(email, password);
    }
}
