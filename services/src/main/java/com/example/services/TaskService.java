package com.example.services;


import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataacces.entity.Task;
import com.example.dataacces.entity.TaskPlayList;
import com.example.dataacces.enums.TaskStatus;
import com.example.dataacces.repository.TaskPlaylistRepository;
import com.example.dataacces.repository.TaskRepository;
import com.example.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskPlaylistRepository playlistRepository;
    private final ModelMapper mapper;

    @Autowired
    public TaskService(TaskRepository repository, TaskPlaylistRepository playlistRepository, ModelMapper mapper) {
        this.repository = repository;
        this.playlistRepository = playlistRepository;
        this.mapper = mapper;
    }

  public List<LimitedTaskDto> readAll(LimitedUserDto currentUser, Long playListId){
      Optional<TaskPlayList> playList = playlistRepository.findById(playListId);
      List<Task> tasks = playList.get().getTasks();
      return tasks.stream().map(x -> mapper.map(x, LimitedTaskDto.class)).toList();
  }


    public TaskDto create(TaskDto taskDto, LimitedUserDto currentUser) throws ValidationException {
        if (taskDto.getTitle() == null){
            throw new ValidationException("please enter title");
        }
        Task task = mapper.map(taskDto, Task.class);
        task.setStatus(TaskStatus.Active);
        if (task.getPlayList() == null){
            task.setPlayList(playlistRepository.findByTitleAndUser_Id("main", currentUser.getId()));
        }
        Task task1 = repository.save(task);
        return taskDto;
    }



    public boolean delete(Long id) {
       repository.deleteById(id);
       return true;
    }

    public TaskDto update(TaskUpdateDto taskUpdateDto) throws ValidationException, NotFoundException {
        if (taskUpdateDto.getId() == null){
            throw new ValidationException("please enter id to update");
        }
        Task task1 = repository.findById(taskUpdateDto.getId()).orElseThrow(NotFoundException::new);
        task1.setTitle(Optional.ofNullable(taskUpdateDto.getTitle()).orElse(task1.getTitle()));
        task1.setDescription(Optional.ofNullable(taskUpdateDto.getDescription()).orElse(task1.getTitle()));
        if (taskUpdateDto.getPlaylistId() == null){
            task1.setPlayList(task1.getPlayList());
        }else {
            TaskPlayList playList = playlistRepository.findById(taskUpdateDto.getPlaylistId()).orElseThrow(NotFoundException::new);
            task1.setPlayList(playList);
        }

        repository.save(task1);
        return mapper.map(task1, TaskDto.class);

    }

    private static void checkValidation(LimitedTaskDto limitedTaskDto) throws ValidationException {
        if (limitedTaskDto.getTitle() == null || limitedTaskDto.getTitle().isEmpty()){
            throw new ValidationException("please enter title");
        }

    }
}


