package com.whisper.persistence.repository;

import com.whisper.persistence.entity.WhisperLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhisperLikeRepository extends JpaRepository<WhisperLike , Long> {
}
