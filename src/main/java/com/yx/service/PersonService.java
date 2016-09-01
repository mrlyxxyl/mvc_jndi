package com.yx.service;

import com.yx.dao.PersonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {
    @Resource
    private PersonDao personDao;

    public List<Map<String, Object>> queryPersons(String serverName) throws SQLException {
        return personDao.queryPersons(serverName);
    }
}
