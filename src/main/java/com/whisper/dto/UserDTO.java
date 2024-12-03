package com.whisper.dto;

import com.whisper.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDTO {
    private String username;
    private Double userPoint;
    private String authorities;
}
