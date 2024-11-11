package com.whisper.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dispute extends BaseEntity {

    @Size(min=2,max=255)
    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "whisper_id")
    @NotNull
    private Whisper whisper;

    @OneToMany
    @Lazy
    @JoinTable(name = "dispute_comment_set", joinColumns = @JoinColumn(name = "dispute_id"), inverseJoinColumns = @JoinColumn(name = "dispute_comment_id"))
    private Set<DisputeComment> disputeComments = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "dispute_tag_id")
    private DisputeTag disputeTag;

    @OneToOne(mappedBy = "dispute")
    private DisputeLike disputeLike;

}
