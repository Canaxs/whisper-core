package com.whisper.persistence.repository;

import com.whisper.persistence.entity.Notification;
import com.whisper.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long> {

    List<Notification> getAllByUser(User user);

    @Query("SELECT COUNT(*) FROM Notification n WHERE n.isRead = false AND n.user =:user ")
    Integer getIsReads(@Param("user") User user);
}
