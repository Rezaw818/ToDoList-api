package com.example.services;


import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataacces.entity.TaskPlayList;
import com.example.dataacces.entity.User;
import com.example.dataacces.repository.TaskPlaylistRepository;
import com.example.dataacces.repository.TaskRepository;
import com.example.dataacces.repository.UserRepository;
import com.example.dto.LimitedUserDto;
import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {

    final private JwtUtil jwtUtil;
    final private ModelMapper mapper;
    final private UserRepository userRepository;
    final private TaskPlaylistRepository taskPlaylistRepository;
    final private TaskRepository taskRepository;

    @Autowired
    public UserService(ModelMapper mapper, UserRepository repository, JwtUtil jwtUtil, UserRepository userRepository, TaskPlaylistRepository taskPlaylistRepository, TaskRepository tastRepository) {
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.taskPlaylistRepository = taskPlaylistRepository;
        this.taskRepository = tastRepository;
    }

    public UserDto create(UserDto userDto) throws ValidationException {
        checkValidation(userDto);
        User user = mapper.map(userDto, User.class);
        TaskPlayList taskPlayList = new TaskPlayList();
        taskPlayList.setUser(user);
        taskPlayList.setTitle("main");
        userRepository.save(user);
        taskPlaylistRepository.save(taskPlayList);

        return userDto;
    }

    private static void checkValidation(UserDto userDto) throws ValidationException {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new ValidationException("please enter name");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new ValidationException("please enter email");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new ValidationException("please enter password");
        }

    }

    public LimitedUserDto getUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
        return mapper.map(user, LimitedUserDto.class);
    }

    public LimitedUserDto getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(NotFoundException::new);
        return mapper.map(user, LimitedUserDto.class);
    }

    public UserDto login(LoginDto loginDto) throws NotFoundException, ValidationException {
        User user = userRepository.findUserByUsername(loginDto.getUsername()).orElseThrow(NotFoundException::new);


        if (!user.getPassword().equals(loginDto.getPassword())){
            throw new ValidationException("password is wrong");
        }

       user.setTaskPlayLists(taskPlaylistRepository.findByUser_Id(user.getId()));
        user.getTaskPlayLists().forEach(playList -> playList.setTasks(taskRepository.findByPlayList_Id(playList.getId())));
        UserDto result = mapper.map(user, UserDto.class);
        result.setToken(jwtUtil.generateToken(result.getUsername()));
        return result;


    }

    public UserDto getUser(String username) throws NotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
       user.setTaskPlayLists(taskPlaylistRepository.findByUser_Id(user.getId()));
       user.getTaskPlayLists().forEach(playList -> playList.setTasks(taskRepository.findByPlayList_Id(playList.getId())));
        return mapper.map(user, UserDto.class);
    }
}
