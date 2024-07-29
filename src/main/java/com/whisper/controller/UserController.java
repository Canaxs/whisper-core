package com.whisper.controller;

import com.whisper.dto.CreateUserRequest;
import com.whisper.dto.UserDTO;
import com.whisper.dto.UsersDTO;
import com.whisper.persistence.entity.User;
import com.whisper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUsername(username));
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UsersDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
