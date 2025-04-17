package com.example.services;


import com.example.common.exceptions.NotFoundException;
import com.example.dataacces.entity.TaskPlayList;
import com.example.dataacces.entity.User;
import com.example.dataacces.repository.TaskPlaylistRepository;
import com.example.dataacces.repository.UserRepository;
import com.example.dto.LimitedUserDto;
import com.example.dto.TaskPlayListDto;
import com.example.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.common.exceptions.ValidationException;


import java.util.List;
import java.util.Optional;

@Service
public class TaskPlaylistService {

    private final TaskPlaylistRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public TaskPlaylistService(TaskPlaylistRepository repository, UserRepository userRepository, ModelMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

  public List<TaskPlayListDto> readAll(LimitedUserDto currentUser){
      List<TaskPlayList> playLists = repository.findByUser_Id(currentUser.getId());
      return playLists.stream().map(x -> mapper.map(x, TaskPlayListDto.class)).toList();
  }


    public TaskPlayListDto create(LimitedUserDto userDto, TaskPlayList taskPlayList) throws ValidationException, NotFoundException {
        checkValidation(taskPlayList);
        User user = userRepository.findUserByUsername(userDto.getUsername()).orElseThrow(NotFoundException::new);
        taskPlayList.setUser(user);
        TaskPlayList playList = repository.save(taskPlayList);
        return mapper.map(playList, TaskPlayListDto.class);
    }



    public boolean delete(Long id) {
       repository.deleteById(id);
       return true;
    }

    public TaskPlayListDto update(TaskPlayList taskPlayList) throws ValidationException {
        checkValidation(taskPlayList);
        TaskPlayList taskCat = repository.getById(taskPlayList.getId());
        taskCat.setTitle(Optional.ofNullable(taskPlayList.getTitle()).orElse(taskCat.getTitle()));
        TaskPlayList playList = repository.save(taskCat);
        return mapper.map(playList, TaskPlayListDto.class);

    }

    private static void checkValidation(TaskPlayList taskPlayList) throws ValidationException {
        if (taskPlayList.getTitle() == null || taskPlayList.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }
    }
}


