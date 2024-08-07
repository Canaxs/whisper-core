package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private Integer numberLike;

    private Integer numberDislike;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Whisper whisper;

    @ManyToMany
    @JoinTable(name = "whisper_like_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
