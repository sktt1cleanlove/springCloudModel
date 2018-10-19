package com.yuantu.eureka_provider.controller;

import com.yuantu.eureka_provider.domian.PamDTO;
import com.yuantu.eureka_provider.entity.Person;
import com.yuantu.eureka_provider.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/getPerson")
    public Person getPerson(@RequestParam Integer id){
        Person person = personService.getPerson(id);
        System.out.println(person);
        return person;
    }

    @PostMapping("/getPersonRest")
    public Person getPersonRest(@RequestBody PamDTO pamDTO){
        Person person = personService.getPerson(pamDTO.getId());
        System.out.println(person);
        return person;
    }

    @GetMapping("/getString")
    public String getString(){
        return "string";
    }


    /**
     * RabbtMQ
     *
     */
    @Value("${env}")
    private String env;

    @GetMapping("/getBusToMq")
    public void getBusToMq(){
        System.out.println(env+".................................");
    }


}
