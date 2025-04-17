package com.example.dto;

import com.example.dataacces.entity.TaskPlayList;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String token;


    private List<TaskPlayListDto> taskPlayLists;
}
