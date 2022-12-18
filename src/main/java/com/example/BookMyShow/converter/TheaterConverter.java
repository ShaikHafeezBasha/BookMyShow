package com.example.BookMyShow.converter;

import com.example.BookMyShow.Model.Theater;
import com.example.BookMyShow.dto.EntryDto.TheaterEntryDto;
import com.example.BookMyShow.dto.ResponseDto.TheaterResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TheaterConverter {

    public static Theater convertDtoToEntity(TheaterEntryDto theaterEntryDto){

        return Theater.builder().address(theaterEntryDto.getAddress())
                .city(theaterEntryDto.getCity()).name(theaterEntryDto.getName()).build();


    }

    public static TheaterResponseDto convertEntityToDto(Theater theaterEntity){

        return TheaterResponseDto.builder().id(theaterEntity.getTheaterId()).name(theaterEntity.getName())
                .city(theaterEntity.getCity()).address(theaterEntity.getAddress())
                .type(theaterEntity.getType())
                .build();
    }
}