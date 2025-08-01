package com.whisper.persistence.repository;

import com.whisper.dto.WhisperDTO;
import com.whisper.dto.WhisperPanelDTO;
import com.whisper.enums.Category;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.Whisper;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WhisperRepository extends JpaRepository<Whisper, Long> , JpaSpecificationExecutor<Whisper> {

    @Query("FROM Whisper w WHERE w.category  = :category AND w.isActive = true ORDER BY w.Id DESC")
    Page<Whisper> findByCategory(Category category, Pageable pageable);

    @Query("SELECT Id FROM Whisper order by Id desc limit 1")
    Long getByIdNumber();

    @Query("SELECT COUNT(*) FROM Whisper w where w.authorName = :authorName")
    Integer getByWhisperCount(@Param("authorName") String authorName);

    @Query("SELECT SUM(w.whisperLike.numberLike) FROM Whisper w where w.authorName = :authorName")
    Integer getByWhispersLikeCount(@Param("authorName") String authorName);

    @Query("SELECT "
            + " new com.whisper.dto.WhisperPanelDTO("
            + " w.Id,"
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + "w.category )"
            + " FROM Whisper w WHERE w.isActive = false AND w.isDelete = false ORDER BY w.Id DESC")
    List<WhisperPanelDTO> getAllByPendingWhispers();

    @Query("SELECT "
            + " new com.whisper.dto.WhisperPanelDTO("
            + " w.Id,"
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + "w.category )"
            + " FROM Whisper w WHERE w.isActive = true AND w.isDelete = false ORDER BY w.Id DESC")
    List<WhisperPanelDTO> getAllByWhispers();

    @Query("SELECT "
            + " new com.whisper.dto.WhisperDTO("
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + " w.category,"
            + " w.urlName,"
            + " w.imageURL,"
            + " w.createdDate)"
            + " FROM Whisper w WHERE w.isActive = true ORDER BY w.whisperLike.numberLike DESC")
    Page<WhisperDTO> getBestUserPoint(Pageable pageable);

    @Query("SELECT "
            + " new com.whisper.dto.WhisperDTO("
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + " w.category,"
            + " w.urlName,"
            + " w.imageURL,"
            + " w.createdDate)"
            + " FROM Whisper w WHERE w.authorName = :username AND w.isActive = true ")
    List<WhisperDTO> getUserWhispers(@Param("username") String username);

    @Query("SELECT "
            + " new com.whisper.dto.WhisperDTO("
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + " w.category,"
            + " w.urlName,"
            + " w.imageURL,"
            + " w.createdDate)"
            + " FROM Whisper w WHERE w.isActive = true"
            + " ORDER BY w.whisperLike.numberLike DESC, w.createdDate DESC ")
    List<WhisperDTO> getTop10Carousel(Pageable pageable);


    @Query("SELECT "
            + " new com.whisper.dto.WhisperDTO("
            + " w.authorName,"
            + " w.title,"
            + " w.description,"
            + " w.source,"
            + " w.category,"
            + " w.urlName,"
            + " w.imageURL,"
            + " w.createdDate)"
            + " FROM Whisper w WHERE w.isActive = true ORDER BY w.whisperLike.numberLike LIMIT 10")
    List<WhisperDTO> getCarouselSmall();

    Whisper findByUrlName(String urlName);

    Page<Whisper> findAll(@Nullable Specification<Whisper> spec, @NonNull Pageable pageable);


}
