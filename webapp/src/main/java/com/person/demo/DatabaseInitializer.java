package com.person.demo;

import com.person.demo.Exception.AppException;
import com.person.demo.Service.PersonService;
import com.person.demo.controller.PersonController;
import com.person.demo.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;

@Component
public class DatabaseInitializer {

    private PersonService personService;

    @Autowired
    public DatabaseInitializer(PersonService personService)
    {
        this.personService=personService;
    }

    @PostConstruct
    public  void populateDatabase()
    {
        Person p1 = new Person();
        p1.setPersonEmail("kandarpvyas@gmail.com");
        p1.setPersonPassword("test");

        Person p2 = new Person();
        p2.setPersonEmail("test.23@gmail.com");
        p2.setPersonPassword("pass123");

        try
        {
            personService.createPerson(p1);
            personService.createPerson(p2);

        }
        catch (AppException e)
        {
            e.printStackTrace();
        }



    }

}
