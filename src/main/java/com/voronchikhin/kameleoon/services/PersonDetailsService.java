package com.voronchikhin.kameleoon.services;

import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.models.Quote;
import com.voronchikhin.kameleoon.repositories.PersonRepository;
import com.voronchikhin.kameleoon.util.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(username);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("Can't find user with this email");
        }

        return new PersonDetails(person.get());
    }

    public List<Quote> getUserQuotes(String username){
        Optional<Person> person = personRepository.findByEmail(username);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("Can't find user with this email");
        }

        return person.get().getQuoteList();
    }
}
