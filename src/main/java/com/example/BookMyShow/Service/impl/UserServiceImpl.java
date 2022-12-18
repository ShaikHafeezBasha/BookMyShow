package com.example.BookMyShow.Service.impl;

import com.example.BookMyShow.Model.User;
import com.example.BookMyShow.Repository.UserRepository;
import com.example.BookMyShow.Service.UserService;
import com.example.BookMyShow.converter.UserConverter;
import com.example.BookMyShow.dto.EntryDto.UserEntryDto;
import com.example.BookMyShow.dto.ResponseDto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(UserEntryDto userEntryDto) {

        User user = UserConverter.convertDtoToEntity(userEntryDto); //Cleaner
        userRepository.save(user);
    }

    @Override
    public UserResponseDto getUser(int id) {

        User user = userRepository.findById(id).get();

        UserResponseDto userResponseDto = UserConverter.convertEntityToDto(user);

        return userResponseDto;
    }
}