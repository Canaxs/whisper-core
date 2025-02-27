package com.whisper.persistence.repository;

import com.whisper.persistence.entity.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<BadgeEntity , Long> {
}
