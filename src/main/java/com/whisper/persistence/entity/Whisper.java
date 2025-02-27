package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whisper.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Whisper extends BaseEntity {

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false , columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String source;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(unique = true)
    private String urlName;

    private String imageURL;

    private Boolean isActive;

    private Boolean isDelete;

    @OneToOne(mappedBy = "whisper")
    @JsonIgnore
    private WhisperLike whisperLike;

    @OneToOne(mappedBy = "whisper")
    private WhisperView whisperView;

    @OneToMany
    @JoinTable(name = "whisper_comment_set", joinColumns = @JoinColumn(name = "whisper_id"), inverseJoinColumns = @JoinColumn(name = "whisper_comment_id"))
    private Set<WhisperComment> whisperComment = new HashSet<>();
}
