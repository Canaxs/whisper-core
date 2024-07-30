package com.whisper.persistence.repository;

import com.whisper.dto.WhisperDTO;
import com.whisper.dto.WhisperPanelDTO;
import com.whisper.enums.Category;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.Whisper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhisperRepository extends JpaRepository<Whisper, Long> {

    @Query("FROM Whisper w WHERE  w.category  = :category ORDER BY w.Id DESC")
    Page<Whisper> findByCategory(Category category, Pageable pageable);

    @Query("SELECT Id FROM Whisper order by Id desc limit 1")
    Long getByIdNumber();

    @Query("SELECT "
            + " new com.whisper.dto.WhisperPanelDTO("
            + " w.Id,"
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + "w.category )"
            + " FROM Whisper w WHERE w.isActive = false ORDER BY w.Id DESC")
    List<WhisperPanelDTO> getAllByPendingWhispers();

    @Query("SELECT "
            + " new com.whisper.dto.WhisperPanelDTO("
            + " w.Id,"
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + "w.category )"
            + " FROM Whisper w ORDER BY w.Id DESC")
    List<WhisperPanelDTO> getAllByWhispers();

    @Query("SELECT "
            + " new com.whisper.dto.WhisperDTO("
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + " w.category,"
            + " w.urlName,"
            + " w.image,"
            + " w.createdDate)"
            + " FROM Whisper w ORDER BY w.whisperLike.numberLike DESC LIMIT 24")
    List<WhisperDTO> getBestUserPoint();

    Whisper findByUrlName(String urlName);
}
