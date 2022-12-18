package com.example.BookMyShow.converter;

import com.example.BookMyShow.Model.Ticket;
import com.example.BookMyShow.dto.ResponseDto.TicketResponseDto;

public class TicketConverter {

    public static TicketResponseDto convertEntityToDto(Ticket ticketEntity){

        return TicketResponseDto.builder().id((int) ticketEntity.getTicketId()).amount(ticketEntity.getAmount())
                .alloted_seats(ticketEntity.getAllottedSeats())
                .build();

    }
}