package com.example.BookMyShow.converter;

import com.example.BookMyShow.Model.Movie;
import com.example.BookMyShow.dto.EntryDto.MovieEntryDto;
import com.example.BookMyShow.dto.ResponseDto.MovieNameAndIdObject;
import com.example.BookMyShow.dto.ResponseDto.MovieResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieConverter {


    public static Movie convertDtoToEntity(MovieEntryDto movieEntryDto){

        return Movie.builder() //.attribute (value)
                .name(movieEntryDto.getName())
                .releaseDate(movieEntryDto.getReleaseDate()).build();

    }


    public static MovieResponseDto convertEntityToDto(Movie movieEntity){

        return MovieResponseDto.builder().id(movieEntity.getMovieId()).releaseDate(movieEntity.getReleaseDate()).name(movieEntity.getName()).build();
    }

    public static MovieNameAndIdObject convertEntityToThisObject(Movie movieEntity){


        return MovieNameAndIdObject.builder().
                id(movieEntity.getMovieId())
                .name(movieEntity.getName()).build();

    }
}