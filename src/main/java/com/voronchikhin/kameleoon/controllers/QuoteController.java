package com.voronchikhin.kameleoon.controllers;

import com.voronchikhin.kameleoon.dto.QuoteDTO;
import com.voronchikhin.kameleoon.dto.QuoteResponseDTO;
import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.models.Quote;
import com.voronchikhin.kameleoon.services.PersonService;
import com.voronchikhin.kameleoon.services.QuoteService;
import com.voronchikhin.kameleoon.util.errors.ErrorBuilder;
import com.voronchikhin.kameleoon.util.errors.ErrorResponse;
import com.voronchikhin.kameleoon.util.errors.QuoteAddingException;
import com.voronchikhin.kameleoon.util.errors.QuoteUpdatingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {
    private final QuoteService quoteService;
    private final PersonService personService;

    public QuoteController(QuoteService quoteService, PersonService personService) {
        this.quoteService = quoteService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<QuoteResponseDTO> findAll(){
        return quoteService.findAll().stream().map(this::convertToQuoteDTO).collect(Collectors.toList());
    }

    @GetMapping("/top")
    public List<Quote> findTop(){
        return quoteService.findTop10();
    }

    @GetMapping("/flop")
    public List<Quote> findFlop(){
        return quoteService.findFlop10();
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid QuoteDTO quoteDTO,
                                             BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new QuoteAddingException(errorBuilder.buildError());
        }

        quoteService.save(convertToQuote(quoteDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id,
                                             @RequestBody @Valid QuoteDTO quoteDTO,
                                             BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new QuoteUpdatingException(errorBuilder.buildError());
        }

        quoteService.update(id, convertToQuote(quoteDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        quoteService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Quote convertToQuote(QuoteDTO quoteDTO){
        Quote quote = new Quote();
        quote.setContent(quoteDTO.getContent());
        quote.setDateOfCreation(new Date());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Person owner = personService.findByEmail(username);
        quote.setOwner(owner);

        return quote;
    }

    private QuoteResponseDTO convertToQuoteDTO(Quote quote){
        return new QuoteResponseDTO(quote.getContent(), quote.getOwner().getId());
    }

    @ExceptionHandler({QuoteUpdatingException.class, QuoteAddingException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exc){
        ErrorResponse response = new ErrorResponse(
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
