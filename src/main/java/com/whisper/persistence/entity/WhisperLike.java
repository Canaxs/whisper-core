package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

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
    private Double numberLike;

    @NotNull
    private Double numberDislike;

    @OneToOne
    @MapsId
    @JsonIgnore
    @NotNull
    private Whisper whisper;

    @ManyToMany
    @JoinTable(name = "whisper_like_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> likeUsers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "whisper_like_point",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "point_id"))
    private Set<UserAndPoint> userPoints = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "whisper_dislike_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> dislikeUsers = new LinkedHashSet<>();
}
