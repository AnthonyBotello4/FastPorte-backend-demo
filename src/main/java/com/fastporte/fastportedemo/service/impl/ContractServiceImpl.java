package com.fastporte.fastportedemo.service.impl;

import com.fastporte.fastportedemo.entities.Contract;
import com.fastporte.fastportedemo.repository.IContractRepository;
import com.fastporte.fastportedemo.service.IContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class ContractServiceImpl implements IContractService {

    private final IContractRepository contractRepository;

    public ContractServiceImpl(IContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional // Para que pueda hacer cambios en la database
    public Contract save(Contract contract) throws Exception {
        return contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        contractRepository.deleteById(id);
    }

    @Override
    public List<Contract> gettAll() throws Exception {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> getById(Long id) throws Exception {
        return contractRepository.findById(id);
    }

    @Override
    public List<Contract> findByDriverId(Long driverId) throws Exception {
        return contractRepository.findByDriverId(driverId);
    }

    @Override
    public List<Contract> findByClientId(Long clientId) throws Exception {
        return contractRepository.findByClientId(clientId);
    }

    @Override
    public List<Contract> findByDriverIdAndClientId(Long driverId, Long clientId) throws Exception {
        return contractRepository.findByDriverIdAndClientId(driverId, clientId);
    }
}
