package com.whisper.dto;

import com.whisper.enums.Category;
import com.whisper.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisputeDTO {

    private Long id;

    private String description;
    private User user;

    private String whisperTitle;
    private String whisperUrlName;
    private Category whisperCategory;
    private String whisperSource;
    private String whisperAuthorName;

    private Integer commentSize;

}
