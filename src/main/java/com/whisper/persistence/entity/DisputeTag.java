package com.whisper.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whisper.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ElementCollection(targetClass = String.class)
    @JoinTable(name = "dispute_tag_tags", joinColumns = @JoinColumn(name = "dispute_tag_id"))
    @Column(name = "tags")
    private Set<String> tags;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Dispute dispute;
}
