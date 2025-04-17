package com.example.dto;


import com.example.dataacces.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TaskDto {


    private Long id;

    private String title;

    private String description;

    private TaskPlayListDto playList;

    private TaskStatus status;



}
