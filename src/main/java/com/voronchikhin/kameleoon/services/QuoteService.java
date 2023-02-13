package com.voronchikhin.kameleoon.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voronchikhin.kameleoon.dto.QuoteDTO;
import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.models.Quote;
import com.voronchikhin.kameleoon.repositories.PersonRepository;
import com.voronchikhin.kameleoon.repositories.QuoteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuoteService {
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> findAll(){
        return quoteRepository.findAll();
    }

    public Quote findById(int id){
        Optional<Quote> foundQuote = quoteRepository.findById(id);

        return foundQuote.orElse(null);
    }

    public List<Quote> findTop10(){
        return quoteRepository.findTop10ByOrderByScoreDesc();
    }

    public List<Quote> findFlop10(){
        return quoteRepository.findTop10ByOrderByScore();
    }

    @Transactional
    public void delete(int id){
        Quote quoteToDelete = findById(id);

        Person personWithThisQuote = quoteToDelete.getOwner();
        if (personWithThisQuote != null){
            personWithThisQuote.getQuoteList().remove(quoteToDelete);
        }

        quoteRepository.deleteById(id);
    }

    @Transactional
    public void save(Quote quote){
        quoteRepository.save(quote);
    }

    @Transactional
    public void update(int id, Quote quote){
        quote.setId(id);
        quoteRepository.save(quote);
    }
}
