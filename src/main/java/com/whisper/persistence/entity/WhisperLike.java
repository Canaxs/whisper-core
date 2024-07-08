package com.whisper.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
