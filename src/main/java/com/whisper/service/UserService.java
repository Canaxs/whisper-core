package com.whisper.service;

import com.whisper.dto.CreateUserRequest;
import com.whisper.dto.UserDTO;
import com.whisper.dto.UsersDTO;
import com.whisper.enums.Role;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(CreateUserRequest request) {

        User newUser = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .userPoint(0)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

    public UserDTO getUser(Long userId) {
        User user = new User();
        try {
            user = userRepository.getReferenceById(userId);
        }
        catch (Exception e) {
            throw new RuntimeException("");
        }
        return UserDTO.builder()
                .username(user.getUsername())
                .userPoint(user.getUserPoint())
                .authorities(user.getAuthorities())
                .build();
    }
    public User deleteUser(Long userId) {
        User user = new User();
        try {
            user = userRepository.getReferenceById(userId);
            userRepository.delete(user);
        }
        catch (Exception e) {
            throw new RuntimeException("Exception: ");
        }
        return user;
    }

    public UserDTO getUsername(String username) {
        User user = new User();
            if(userRepository.findByUsername(username).isPresent()) {
                user = userRepository.findByUsername(username).get();
            }
            else {
                throw new RuntimeException("");
            }
            return UserDTO.builder()
                    .username(user.getUsername())
                    .userPoint(user.getUserPoint())
                    .authorities(user.getAuthorities())
                    .build();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getMods() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_MOD);
        return userRepository.findByAuthoritiesIn(roles);
    }

    public User updateAuthorities(Long userId, String role) {
        User user = userRepository.getReferenceById(userId);
        Set<Role> roles = user.getAuthorities();
        if(role.equalsIgnoreCase("MOD")) {
            roles.add(Role.ROLE_MOD);
        }
        else if (role.equalsIgnoreCase("ADMIN")) {
            roles.add(Role.ROLE_ADMIN);
        }
        return userRepository.save(user);
    }
}