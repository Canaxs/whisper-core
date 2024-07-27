package com.whisper.dto;

import com.whisper.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhisperPanelDTO {
    private Long Id;
    private String authorName;
    private String title;
    private String description;
    private String source;
    private Category category;
}
