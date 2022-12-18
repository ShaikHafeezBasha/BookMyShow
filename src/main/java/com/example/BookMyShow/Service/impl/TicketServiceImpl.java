package com.example.BookMyShow.Service.impl;

import com.example.BookMyShow.Model.Show;
import com.example.BookMyShow.Model.ShowSeats;
import com.example.BookMyShow.Model.Ticket;
import com.example.BookMyShow.Model.User;
import com.example.BookMyShow.Repository.ShowRepository;
import com.example.BookMyShow.Repository.TicketRepository;
import com.example.BookMyShow.Repository.UserRepository;
import com.example.BookMyShow.Service.TicketService;
import com.example.BookMyShow.converter.ShowConvertor;
import com.example.BookMyShow.converter.TicketConverter;
import com.example.BookMyShow.converter.UserConverter;
import com.example.BookMyShow.dto.BookTicketRequestDto;
import com.example.BookMyShow.dto.ResponseDto.TicketResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public TicketResponseDto getTicket(int id) {

        Ticket ticket = ticketRepository.findById(id).get();

        UserConverter TicketConvertor;
        TicketResponseDto ticketResponseDto = TicketConverter.convertEntityToDto(ticket);

        return ticketResponseDto;

    }

    @Override
    public TicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) {


        User user = userRepository.findById(bookTicketRequestDto.getId()).get();
        Show show = showRepository.findById(bookTicketRequestDto.getShowId()).get();

        Set<String> requestSeats = bookTicketRequestDto.getRequestedSeats();


        List<ShowSeats> showSeatsList = show.getSeats();


//        //Another way to iterate. Try to study about it.
//        List<ShowSeatsEntity> bookedSeats = showSeatsEntityList
//                .stream()
//                .filter(seat -> seat.getSeatType().equals(bookTicketRequestDto.getSeatType())&&!seat.isBooked()&&
//                        requestSeats.contains(seat.getSeatNumber()))
//                .collect(Collectors.toList());



        List<ShowSeats> bookedSeats = new ArrayList<>();

        for(ShowSeats seat :showSeatsList){

            if(!seat.isBooked()&&seat.getSeatType().equals(bookTicketRequestDto.getSeatType())&&requestSeats.contains(seat.getSeatNumber())){
                bookedSeats.add(seat);
            }
        }

        if(bookedSeats.size()!=requestSeats.size()){
            //Al the seats were not avaiable
            throw new Error("All Seats not available");
        }

        //Step 2

        Ticket ticket = Ticket.builder().
                user(user)
                .show(show)
                .seats(bookedSeats).
                build();



        //Step 3 :

        double amount = 0;

        for(ShowSeats showSeats: bookedSeats){

            showSeats.setBooked(true);
            showSeats.setBookedAt(new Date());
            showSeats.setTicket(ticket);

            amount = amount + showSeats.getRate();
        }

        ticket.setBookedAt(new Date());
        ticket.setAllottedSeats(convertListOfSeatsEntityToString(bookedSeats));
        ticket.setAmount(amount);


        //Connect in thw Show and the user

        show.getTickets().add(ticket);


        //Add the ticket in the tickets list of the user Entity
        user.getTicketEntities().add(ticket);


        ticket = ticketRepository.save(ticket);

        ShowConvertor TicketConvertor;
        return TicketConverter.convertEntityToDto(ticket);


    }

    public String convertListOfSeatsEntityToString(List<ShowSeats> bookedSeats){

        String str = "";
        for(ShowSeats showSeats : bookedSeats){

            str = str + showSeats.getSeatNumber()+" ";
        }

        return str;

    }
}