package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.PersonTypeDao;
import com.happyweekend.models.PersonType;
import com.happyweekend.service.interfaces.IPersonTypeService;

import java.util.List;

public class PersonTypeService implements IPersonTypeService {
    @Override
    public List<PersonType> getAll() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        PersonTypeDao dao = new PersonTypeDao(connectionManager.connect());
        return dao.getAll();
    }
}
