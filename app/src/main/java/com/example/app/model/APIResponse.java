package com.example.app.model;

import com.example.app.model.enums.APIStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String message = "";
    private APIStatus status;
    private T data;
}
