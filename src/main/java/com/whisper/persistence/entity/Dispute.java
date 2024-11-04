package com.whisper.persistence.entity;

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
public class Dispute extends BaseEntity {

    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "whisper_id")
    private Whisper whisper;

    @OneToMany
    @JoinTable(name = "dispute_comment_set", joinColumns = @JoinColumn(name = "dispute_id"), inverseJoinColumns = @JoinColumn(name = "dispute_comment_id"))
    private Set<DisputeComment> disputeComments = new HashSet<>();

}
