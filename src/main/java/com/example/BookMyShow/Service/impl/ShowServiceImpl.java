package com.example.BookMyShow.Service.impl;

import com.example.BookMyShow.Model.*;
import com.example.BookMyShow.Repository.*;
import com.example.BookMyShow.Service.ShowService;
import com.example.BookMyShow.converter.ShowConvertor;
import com.example.BookMyShow.dto.EntryDto.ShowEntryDto;
import com.example.BookMyShow.dto.ResponseDto.ShowResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j //Helps
@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    ShowSeatsRepository showSeatsRepository;

    @Autowired
    ShowRepository showRepository;

    @Override
    public ShowResponseDto addShow(ShowEntryDto showEntryDto) {

        Show show = ShowConvertor.convertDtoToEntity(showEntryDto);

        //MovieEntity
        Movie movieEntity = movieRepository.findById(showEntryDto.getMovieResponseDto().getId()).get();

        Theater theaterEntity = theaterRepository.findById(showEntryDto.getTheaterResponseDto().getId()).get();


        show.setMovie(movieEntity); //Why are we setting these varibble
        show.setTheater(theaterEntity);

        show = showRepository.save(show);


        //We need to pass the list of the theater seats
        generateShowEntitySeats(theaterEntity.getSeats(),show);



        //We need to create Response Show Dto.

        ShowResponseDto showResponseDto = ShowConvertor.convertEntityToDto(show,showEntryDto);

        return showResponseDto;
    }

    public void generateShowEntitySeats(List<TheaterSeats> theaterSeatsList, Show show){

        List<ShowSeats> showSeatsList = new ArrayList<>();

        //log.info(String.valueOf(theaterSeatsEntityList));
//        log.info("The list of theaterEntity Seats");
//        for(TheaterSeatsEntity tse: theaterSeatsEntityList){
//            log.info(tse.toString());
//        }


        for(TheaterSeats tse : theaterSeatsList){

            ShowSeats showSeats = ShowSeats.builder().seatNumber(tse.getSeatNumber())
                    .seatType(tse.getSeatType())
                    .rate(tse.getRate())
                    .build();

            showSeatsList.add(showSeats);
        }


        //We need to set the show Entity for each of the ShowSeat....
        for(ShowSeats showSeatsEntity:showSeatsList){
            showSeatsEntity.setShow(show);
        }

        showSeatsRepository.saveAll(showSeatsList);


        show.setSeats(showSeatsList);

    }

}
