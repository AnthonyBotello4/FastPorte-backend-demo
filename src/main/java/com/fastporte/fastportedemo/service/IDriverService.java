package com.fastporte.fastportedemo.service;

import com.fastporte.fastportedemo.entities.Driver;

public interface IDriverService extends CrudService<Driver> {
    Driver findByEmailAndPassword(String email, String password);

}
