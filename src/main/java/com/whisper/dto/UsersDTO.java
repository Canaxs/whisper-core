package com.whisper.dto;

import com.whisper.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long Id;
    private String username;
    private Integer userPoint;
}
