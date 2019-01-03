package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.PersonTypeDao;
import com.happyweekend.models.PersonType;
import com.happyweekend.service.interfaces.IPersonTypeService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonTypeService implements IPersonTypeService {
    @Override
    public List<PersonType> getAll() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Connection con = connectionManager.connect();
        PersonTypeDao dao = new PersonTypeDao(con);
        List<PersonType> list = dao.getAll();
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
