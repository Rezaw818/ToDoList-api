package com.example.dto;


import com.example.dataacces.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TaskUpdateDto {


    private Long id;

    private String title;

    private String description;

    private Long playlistId;


}
