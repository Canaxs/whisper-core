package com.whisper.persistence.repository;

import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.UserAndPoint;
import com.whisper.persistence.entity.Whisper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAndPointRepository extends JpaRepository<UserAndPoint , Long> {


    @Query("FROM UserAndPoint u where u.user =:user and u.whisper =:whisper")
    UserAndPoint getUserAndPointByWhisperAndUser(@Param("user") User user,
                                                 @Param("whisper") Whisper whisper);
}
