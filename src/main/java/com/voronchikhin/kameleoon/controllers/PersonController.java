package com.voronchikhin.kameleoon.controllers;

import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.models.Quote;
import com.voronchikhin.kameleoon.services.PersonService;
import com.voronchikhin.kameleoon.util.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<Person> all(){
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id){
        return personService.findById(id);
    }

}
