package com.whisper.persistence.repository;

import com.whisper.persistence.entity.WhisperView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhisperViewRepository extends JpaRepository<WhisperView, Long> {
}
