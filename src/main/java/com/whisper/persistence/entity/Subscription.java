package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription extends BaseEntity {

    private String planName;

    @OneToOne
    @JsonIgnore
    private User user;

    private Integer writeLimit;

    private Integer writeLimitDef;

    private Boolean earning;

    private Boolean exclusive;
}
