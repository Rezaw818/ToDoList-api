package com.example.app.controller.base;

import com.example.app.model.APIResponse;
import com.example.common.exceptions.ValidationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface CreateController<Dto> {

    @PostMapping("add")
    APIResponse<Dto> add(@RequestBody Dto dto) throws ValidationException;

}
