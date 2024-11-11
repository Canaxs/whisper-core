package com.whisper.persistence.repository;

import com.whisper.dto.DisputeDTO;
import com.whisper.dto.GetDisputeDTO;
import com.whisper.dto.TagsDTO;
import com.whisper.persistence.entity.Dispute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute,Long> {

    @Query("FROM Dispute d ORDER BY d.Id DESC")
    Page<Dispute> getAllDispute(Pageable pageable);

    @Query("SELECT" +
            " new com.whisper.dto.TagsDTO(t,COUNT(*))" +
            " FROM Dispute d JOIN d.disputeTag.tags t" +
            " WHERE (d.createdDate > :interimDate or d.createdDate > :interimDate2 or d.createdDate > :interimDate3) " +
            " GROUP BY t HAVING COUNT(*) > 1")
    List<TagsDTO> getMostUsedTags(@Param("interimDate") Date interimDate,
                                  @Param("interimDate2") Date interimDate2 ,
                                  @Param("interimDate3") Date interimDate3);

    @Query("SELECT " +
            " new com.whisper.dto.GetDisputeDTO(" +
            " d.Id," +
            " d.description, " +
            " d.user," +
            " d.createdDate," +
            " d.whisper.title," +
            " d.whisper.urlName," +
            " d.whisper.category," +
            " d.whisper.source," +
            " d.whisper.authorName," +
            " d.whisper.imageURL," +
            " d.disputeTag, " +
            " size(d.disputeComments) )" +
            " FROM Dispute d JOIN d.disputeTag.tags t " +
            " WHERE t =:disputeTag ORDER BY d.Id DESC ")
    List<GetDisputeDTO> getDisputeTags(@Param("disputeTag") String disputeTag);
}
