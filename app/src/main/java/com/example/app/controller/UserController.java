package com.example.app.controller;


import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dto.LimitedUserDto;
import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.services.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    final private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }



    @PostMapping("signUp")
    public APIResponse<UserDto> signUp(@RequestBody UserDto userDto) throws ValidationException, NotFoundException {
        try{
            LimitedUserDto userByEmail = service.getUserByEmail(userDto.getEmail());
        } catch (NotFoundException e) {
            return APIResponse.<UserDto>builder()
                    .status(APIStatus.Error)
                    .message("you already signed up")
                    .build();
        }


        return APIResponse.<UserDto>builder()
                .data(service.create(userDto))
                .status(APIStatus.Success)
                .build();
    }

    @PostMapping("signIn")
    public APIResponse<UserDto> signIn(@RequestBody LoginDto loginDto) throws ValidationException, NotFoundException {

        //Todo = do filter jwt for setting current user in requestServlet except api/user/signIn and signUp
        //Todo = encoder password for user
        try {
            return APIResponse.<UserDto>builder()
                    .data(service.login(loginDto))
                    .status(APIStatus.Success)
                    .build();
        }catch (Exception e){
            return APIResponse.<UserDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }

    }

    @GetMapping
    public APIResponse<UserDto> getUser(@RequestParam("username") String username) throws NotFoundException {
        return APIResponse.<UserDto>builder()
                .data(service.getUser(username))
                .status(APIStatus.Success)
                .build();
    }
}
