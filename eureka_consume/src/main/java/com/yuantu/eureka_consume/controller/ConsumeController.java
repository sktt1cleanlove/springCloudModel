package com.yuantu.eureka_consume.controller;

import com.yuantu.eureka_consume.client.ConsumeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeController {

    @Autowired
    private ConsumeClient consumeClient;

    @PostMapping("/person")
    public String consumePerson(@RequestParam Integer id){
        String person = consumeClient.getProviderPerson(id);
        System.out.println(person);
        return person;

    }
}
