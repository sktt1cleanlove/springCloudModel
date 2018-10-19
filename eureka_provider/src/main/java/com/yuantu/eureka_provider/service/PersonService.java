package com.yuantu.eureka_provider.service;

import com.yuantu.eureka_provider.dao.PersonDao;
import com.yuantu.eureka_provider.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    public Person getPerson(Integer id){
        return personDao.findAllById(id);
    }
}
