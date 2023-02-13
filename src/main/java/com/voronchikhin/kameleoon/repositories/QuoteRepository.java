package com.voronchikhin.kameleoon.repositories;

import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    List<Quote> findTop10ByOrderByScoreDesc();
    List<Quote> findTop10ByOrderByScore();
}
