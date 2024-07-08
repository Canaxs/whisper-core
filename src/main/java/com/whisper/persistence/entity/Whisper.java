package com.whisper.persistence.entity;

import com.whisper.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Whisper extends BaseEntity {

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String source;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String image;

    private Boolean isActive;

    private Boolean isDelete;

    @OneToOne(mappedBy = "whisper")
    private WhisperLike whisperLike;

    @OneToOne(mappedBy = "whisper")
    private WhisperView whisperView;
}
