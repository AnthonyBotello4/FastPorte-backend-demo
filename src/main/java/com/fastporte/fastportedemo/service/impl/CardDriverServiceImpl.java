package com.fastporte.fastportedemo.service.impl;

import com.fastporte.fastportedemo.entities.CardDriver;
import com.fastporte.fastportedemo.repository.ICardDriverRepository;
import com.fastporte.fastportedemo.service.ICardDriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CardDriverServiceImpl implements ICardDriverService {
    private ICardDriverRepository cardDriverRepository;

    public CardDriverServiceImpl(ICardDriverRepository cardDriverRepository) {
        this.cardDriverRepository = cardDriverRepository;
    }

    @Override
    @Transactional
    public CardDriver save(CardDriver entity) throws Exception {
        return cardDriverRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        cardDriverRepository.deleteById(id);
    }

    @Override
    public List<CardDriver> getAll() throws Exception {
        return cardDriverRepository.findAll();
    }

    @Override
    public Optional<CardDriver> getById(Long id) throws Exception {
        return cardDriverRepository.findById(id);
    }

}
