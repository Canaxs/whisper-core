package com.whisper.persistence.repository;

import com.whisper.persistence.entity.DisputeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeCommentRepository extends JpaRepository<DisputeComment , Long> {
}
