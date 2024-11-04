package com.whisper.persistence.repository;

import com.whisper.dto.DisputeDTO;
import com.whisper.persistence.entity.Dispute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute,Long> {

    @Query("FROM Dispute d ORDER BY d.Id DESC")
    Page<Dispute> getAllDispute(Pageable pageable);
}
