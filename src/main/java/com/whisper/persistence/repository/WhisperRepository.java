package com.whisper.persistence.repository;

import com.whisper.enums.Category;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.Whisper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WhisperRepository extends JpaRepository<Whisper, Long> {

    @Query("FROM Whisper w WHERE  w.category  = :category ORDER BY w.Id DESC")
    Page<Whisper> findByCategory(Category category, Pageable pageable);
}
