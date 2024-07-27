package com.whisper.dto;

import com.whisper.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class WhisperDTO {
    private String authorName;
    private String title;
    private String description;
    private String source;
    private String category;
    private String urlName;
    private String image;
    private Date createdDate;
}
