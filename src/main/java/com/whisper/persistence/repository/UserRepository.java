package com.whisper.persistence.repository;

import com.whisper.dto.UserDTO;
import com.whisper.dto.UsersDTO;
import com.whisper.enums.Role;
import com.whisper.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String userName);

    /*@Query(value = "SELECT "
            + " new com.whisper.dto.UsersDTO("
            + " u.Id,"
            + " u.username,"
            + " u.userPoint,"
            + " u.authorities ) "
            + " FROM User u ORDER BY u.Id DESC")
    List<User> findAllByUser();
     */


    /*
    @Query("SELECT "
            + " new com.whisper.dto.UsersDTO("
            + " u.Id,"
            + " u.username,"
            + " u.userPoint )"
            + " FROM User u Where u.authorities in :authorities")
    List<UsersDTO> findAllAuthorities(List<Role> authorities);
     */

    List<User> findByAuthoritiesIn(Set<Role> role);


}
