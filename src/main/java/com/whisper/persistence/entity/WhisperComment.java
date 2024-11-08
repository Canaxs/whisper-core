package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhisperComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Size(min=2,max=255)
    @NotNull
    private String comment;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @JsonIgnore
    @NotNull
    private Whisper whisper;
}
