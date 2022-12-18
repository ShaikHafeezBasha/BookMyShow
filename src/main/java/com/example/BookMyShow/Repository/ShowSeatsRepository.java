package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Model.ShowSeats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatsRepository extends JpaRepository<ShowSeats,Integer> {
}