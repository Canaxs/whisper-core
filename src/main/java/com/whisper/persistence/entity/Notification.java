package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    private String text;

    private String actionInfo;

    private Boolean isRead;

    private Boolean isActive;

    @ManyToOne
    @JsonIgnore
    private User user;
}
