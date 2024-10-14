package com.whisper.dto;

import com.whisper.enums.Category;
import com.whisper.persistence.entity.WhisperComment;
import com.whisper.persistence.entity.WhisperLike;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlWhisperDTO {
	private Long Id;
    private String authorName;
    private String title;
    private String description;
    private String source;
    private String category;
    private String urlName;
    private String image;
    private Date createdDate;
    private WhisperLike whisperLike;
    private Set<WhisperComment> whisperComment;
}
