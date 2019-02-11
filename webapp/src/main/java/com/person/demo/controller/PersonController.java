package com.person.demo.controller;

import com.person.demo.Exception.AppException;
import com.person.demo.Repository.PersonRepository;
import com.person.demo.Service.MyUserDetailsService;
import com.person.demo.Service.PersonService;
import com.person.demo.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userService;

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAllPerson() throws AppException{
        List<Person> personList = personService.getAllPerson();
        return ResponseEntity.ok(personList);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> createPerson(@Valid @RequestBody Person person) throws AppException {
        Person person1 = personService.createPerson(person);
        if(person1!= null)
            return ResponseEntity.ok("User created");
        else
            return ResponseEntity.ok("User already exists");
    }

    @GetMapping("/time")
    public String getTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        String email = userService.userName;
        if(personRepository.findPersonByPersonEmail(email)!=null)
            return date;
        else
            return "Unauthorized";
    }

    @PostMapping(value = "/login")
    public String verifyPerson(@Valid @RequestBody Person person) throws AppException{
        List<Person> personList = personService.getAllPerson();
        for(Person p : personList){
            if(p.getPersonEmail().equals(person.getPersonEmail())){
                CharSequence verifyPassword = person.getPersonPassword();
                System.out.println(p.getPersonPassword());
                if(passwordEncoder.matches(verifyPassword,p.getPersonPassword())){
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String date = timestamp.toString();
                    return date;
                }
            }
        }
        return "Please enter proper Credentials";
    }

    @GetMapping("/ping")
    public String getPing()
    {
        return "You have hit the Ping Service.";
    }

}
