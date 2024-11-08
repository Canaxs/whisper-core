package com.whisper.persistence.repository;

import com.whisper.persistence.entity.DisputeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeTagRepository extends JpaRepository<DisputeTag , Long> {
}
