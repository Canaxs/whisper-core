package com.whisper.controller;

import com.whisper.dto.*;
import com.whisper.persistence.entity.User;
import com.whisper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/updatePlan")
    public ResponseEntity<User> updatePlan(@RequestBody UpdatePlanReq updatePlanReq) {
        return ResponseEntity.ok(userService.updatePlan(updatePlanReq));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserReq updateUserReq) {
        return ResponseEntity.ok(userService.updateUser(updateUserReq));
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/getMods")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<List<User>> getMods() {
        return ResponseEntity.ok(userService.getMods());
    }

    @PutMapping("/updateRole")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<User> updateRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(userService.updateAuthorities(roleDTO.getUserId(), roleDTO.getRole()));
    }
}
