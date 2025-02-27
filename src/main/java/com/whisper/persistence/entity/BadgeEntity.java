package com.whisper.persistence.entity;


import com.whisper.enums.BadgeType;
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
public class BadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Column(unique = true)
    @Size(min=2,max=20)
    private String badge;

    @NotNull
    @Size(min=10,max=255)
    private String badgeURL;

    @NotNull
    private BadgeType badgeType;

}
