package com.alpha.theaterService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.theaterService.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);
    
    List<Movie> findByTitleContainingIgnoreCase(String title);

}