package com.example.app.controller.base;

import com.example.app.model.APIResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReadController<Dto> {

    @GetMapping("")
    APIResponse<List<Dto>> getAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size);

}
