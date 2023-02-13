package com.voronchikhin.kameleoon.services;

import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findById(int id){
        Optional<Person> foundPerson = personRepository.findById(id);

        return foundPerson.orElse(null);
    }

    public Person findByEmail(String email){
        Optional<Person> foundPerson = personRepository.findByEmail(email);

        return foundPerson.orElse(null);
    }
}
