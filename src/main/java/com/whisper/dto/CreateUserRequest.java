package com.whisper.dto;

import com.whisper.enums.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String username,
        String password,
        Set<Role> authorities
){
}
