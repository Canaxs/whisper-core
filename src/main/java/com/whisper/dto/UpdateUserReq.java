package com.whisper.dto;

import lombok.Data;

@Data
public class UpdateUserReq {
    private String username;
    private String password;
}
