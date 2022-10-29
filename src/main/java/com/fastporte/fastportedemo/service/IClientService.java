package com.fastporte.fastportedemo.service;

import com.fastporte.fastportedemo.entities.Client;

public interface IClientService extends CrudService<Client> {

    Client findByEmailAndPassword(String email, String password) throws Exception;
}
