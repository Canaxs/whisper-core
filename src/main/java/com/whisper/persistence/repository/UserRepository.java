package com.whisper.persistence.repository;

import com.whisper.dto.UserDTO;
import com.whisper.dto.UsersDTO;
import com.whisper.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String userName);

    @Query("SELECT "
            + " new com.whisper.dto.UsersDTO("
            + " u.Id,"
            + " u.username,"
            + " u.userPoint )"
            + " FROM User u ORDER BY u.Id DESC")
    List<UsersDTO> findAllByUser();
}
