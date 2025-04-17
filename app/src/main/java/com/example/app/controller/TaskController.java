package com.example.app.controller;


import com.example.app.controller.base.DeleteController;
import com.example.app.controller.base.UpdateController;
import com.example.app.model.APIResponse;
import com.example.app.model.enums.APIStatus;
import com.example.common.exceptions.NotFoundException;
import com.example.common.exceptions.ValidationException;
import com.example.dataacces.entity.Task;
import com.example.dto.*;
import com.example.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/panel/task")
public class TaskController implements
        DeleteController<LimitedTaskDto> {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("add")
    public APIResponse<TaskDto> add(@RequestBody TaskDto task, HttpServletRequest request) throws ValidationException {
        LimitedUserDto currentUser =(LimitedUserDto) request.getAttribute("CURRENT_USER");
        return APIResponse.<TaskDto>builder()
                .status(APIStatus.Success)
                .data(service.create(task, currentUser))
                .build();
    }

    @Override
    public APIResponse<Boolean> delete(Long id) throws NotFoundException {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.Success)
                .data(service.delete(id))
                .build();
    }


    @GetMapping("{playListId}")
    public APIResponse<List<LimitedTaskDto>> getAll(HttpServletRequest request, @PathVariable Long playListId) {
        LimitedUserDto currentUser =(LimitedUserDto) request.getAttribute("CURRENT_USER");
        return APIResponse.<List<LimitedTaskDto>>builder()
                .status(APIStatus.Success)
                .data(service.readAll(currentUser, playListId))
                .build();
    }

    @PutMapping("edit")
    public APIResponse<TaskDto> edit(@RequestBody TaskUpdateDto taskUpdateDto) throws Exception {
        try{
            return APIResponse.<TaskDto>builder()
                    .status(APIStatus.Success)
                    .data(service.update(taskUpdateDto))
                    .build();
        } catch (ValidationException e) {
            return APIResponse.<TaskDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }

    }
}
