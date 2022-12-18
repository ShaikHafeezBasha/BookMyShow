package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

    boolean existsByName(String name);
}