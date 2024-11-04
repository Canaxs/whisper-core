package com.whisper.persistence.repository;

import com.whisper.persistence.entity.DisputeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeLikeRepository extends JpaRepository<DisputeLike , Long> {
}
