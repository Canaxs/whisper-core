package com.whisper.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
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
    private Whisper whisper;

    @ManyToMany
    @JoinTable(name = "whisper_like_user",joinColumns = @JoinColumn(name = "whisper_like_id") , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();
}
