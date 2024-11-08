package com.whisper.persistence.repository;

import com.whisper.persistence.entity.DisputeLike;
import com.whisper.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeLikeRepository extends JpaRepository<DisputeLike , Long> {

    @Query("SELECT count(*) > 0 from DisputeLike d where d.Id = :disputeId and :user member d.likeUsers")
    Boolean controlLike(Long disputeId,User user);

    @Query("SELECT count(*) > 0 from DisputeLike d where d.Id = :disputeId and :user member d.dislikeUsers")
    Boolean controlDislike(Long disputeId,User user);
}
