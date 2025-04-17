package com.example.app.controller;

import com.example.app.controller.base.DeleteController;
import com.example.app.controller.base.ReadController;
import com.example.app.controller.base.UpdateController;
import com.example.app.filter.JwtFilter;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataacces.entity.TaskPlayList;
import com.example.dataacces.entity.User;
import com.example.dto.LimitedUserDto;
import com.example.dto.TaskPlayListDto;
import com.example.dto.UserDto;
import com.example.services.TaskPlaylistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/panel/taskPlaylist")
public class TaskPlaylistController implements DeleteController<TaskPlayList>{

    private final TaskPlaylistService service;

    @Autowired
    public TaskPlaylistController(TaskPlaylistService service) {
        this.service = service;
    }


    @PostMapping("add")
    public APIResponse<TaskPlayListDto> add(@RequestBody TaskPlayList taskPlayList, HttpServletRequest request) throws ValidationException, NotFoundException {
        LimitedUserDto currentUser = (LimitedUserDto) request.getAttribute("CURRENT_USER");
        return APIResponse.<TaskPlayListDto>builder()
                .status(APIStatus.Success)
                .data(service.create(currentUser, taskPlayList))
                .build();
    }

    @Override
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.Success)
                .data(service.delete(id))
                .build();
    }

    @GetMapping
    public APIResponse<List<TaskPlayListDto>> getAll(Integer page, Integer size, HttpServletRequest request) {
        LimitedUserDto currentUser =(LimitedUserDto) request.getAttribute("CURRENT_USER");
        return APIResponse.<List<TaskPlayListDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAll(currentUser))
                .build();
    }

    @PutMapping("edit")
    public APIResponse<TaskPlayListDto> edit(@RequestBody TaskPlayList taskPlayList) throws Exception {
        return APIResponse.<TaskPlayListDto>builder()
                .status(APIStatus.Success)
                .data(service.update(taskPlayList))
                .build();
    }
}
