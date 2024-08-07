package com.whisper.persistence.repository;

import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.WhisperLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WhisperLikeRepository extends JpaRepository<WhisperLike , Long> {

    @Query("SELECT count(*) > 0 from WhisperLike w where w.Id = :whisperId and :user member w.users")
    Boolean controlLike(Long whisperId,User user);
}
