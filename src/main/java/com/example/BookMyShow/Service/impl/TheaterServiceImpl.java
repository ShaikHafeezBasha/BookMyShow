package com.example.BookMyShow.Service.impl;

import com.example.BookMyShow.Model.Theater;
import com.example.BookMyShow.Model.TheaterSeats;
import com.example.BookMyShow.Repository.TheaterRepository;
import com.example.BookMyShow.Repository.TheaterSeatsRepository;
import com.example.BookMyShow.Service.TheaterService;
import com.example.BookMyShow.converter.TheaterConverter;
import com.example.BookMyShow.dto.EntryDto.TheaterEntryDto;
import com.example.BookMyShow.dto.ResponseDto.TheaterResponseDto;
import com.example.BookMyShow.enums.SeatType;
import com.example.BookMyShow.enums.TheaterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    TheaterSeatsRepository theaterSeatsRepository;

    @Override
    public TheaterResponseDto addTheater(TheaterEntryDto theaterEntryDto) {

        Theater theater = TheaterConverter.convertDtoToEntity(theaterEntryDto);


        //create the Seats
        List<TheaterSeats> seats = createTheaterSeats();


        theater.setSeats(seats);
        //I need to set the theaterId for all these seats

        theater.setShows(null);

        for(TheaterSeats theaterSeats:seats){
            theaterSeats.setTheater(theater);
        }
        theater.setType(TheaterType.SINGLE);

        log.info("The theater entity is "+ theater);

        theater = theaterRepository.save(theater);

        TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToDto(theater);


        return theaterResponseDto;

    }

    List<TheaterSeats> createTheaterSeats(){


        List<TheaterSeats> seats = new ArrayList<>();

        seats.add(getTheaterSeat("C1",100,SeatType.CLASSIC));
        seats.add(getTheaterSeat("C2",100,SeatType.CLASSIC));
        seats.add(getTheaterSeat("C3",100,SeatType.CLASSIC));
        seats.add(getTheaterSeat("C4",100,SeatType.CLASSIC));
        seats.add(getTheaterSeat("C5",100,SeatType.CLASSIC));

        seats.add(getTheaterSeat("P1",100,SeatType.PREMIUM));
        seats.add(getTheaterSeat("P2",100,SeatType.PREMIUM));
        seats.add(getTheaterSeat("P3",100,SeatType.PREMIUM));
        seats.add(getTheaterSeat("P4",100,SeatType.PREMIUM));
        seats.add(getTheaterSeat("P5",100,SeatType.PREMIUM));


        theaterSeatsRepository.saveAll(seats);

        return seats;
        //Add in this TheaterSeatEntity type

    }

    TheaterSeats getTheaterSeat(String seatName, int rate, SeatType seatType){

        return TheaterSeats.builder().seatNumber(seatName).rate(rate).seatType(seatType).build();
    }

    //Seperate function will be create...


    @Override
    public TheaterResponseDto getTheater(int id) {

        Theater theater = theaterRepository.findById(id).get();

        TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToDto(theater);

        return theaterResponseDto;
    }
}