package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhisperLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    private Integer numberLike;

    @NotNull
    private Integer numberDislike;

    @OneToOne
    @MapsId
    @JsonIgnore
    @NotNull
    private Whisper whisper;

    @ManyToMany
    @JoinTable(name = "whisper_like_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> likeUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "whisper_dislike_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> dislikeUsers = new HashSet<>();
}
