package com.yuantu.eureka_provider.dao;

import com.yuantu.eureka_provider.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, Integer>{
    Person findAllById(Integer id);
}
