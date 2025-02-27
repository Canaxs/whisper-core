package com.whisper.controller;

import com.whisper.dto.*;
import com.whisper.persistence.entity.BadgeEntity;
import com.whisper.persistence.entity.Subscription;
import com.whisper.persistence.entity.User;
import com.whisper.service.BadgeService;
import com.whisper.service.SubscriptionService;
import com.whisper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    private final SubscriptionService subscriptionService;

    private final BadgeService badgeService;

    public UserController(UserService userService, SubscriptionService subscriptionService, BadgeService badgeService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.badgeService = badgeService;
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

    @GetMapping("/getSubscribe")
    public ResponseEntity<Subscription> getSubscribe() {
        return ResponseEntity.ok(subscriptionService.getSubscribe());
    }

    @GetMapping("/writeLimitDrop")
    public ResponseEntity<Boolean> writeLimitDrop() {
        return ResponseEntity.ok(subscriptionService.writeLimitDrop());
    }

    @PostMapping("/createBadge")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BadgeEntity> createBadge(@RequestPart("badgeCreateRequest") BadgeCreateRequest badgeCreateRequest,
                                                   @RequestPart("image") MultipartFile imageFile) {
        return ResponseEntity.ok(badgeService.createBadge(imageFile,badgeCreateRequest));
    }

    @PostMapping("/addBadge")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<User> addBadge(@RequestBody UserBadgeAddRequest userBadgeAddRequest) {
        return ResponseEntity.ok(badgeService.addBadge(userBadgeAddRequest));
    }

    @DeleteMapping("/deleteBadge")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<User> deleteBadge(@RequestBody UserBadgeAddRequest userBadgeAddRequest) {
        return ResponseEntity.ok(badgeService.deleteBadge(userBadgeAddRequest));
    }

    @GetMapping("/allBadges")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<List<BadgeEntity>> allBadges() {
        return ResponseEntity.ok(badgeService.getAll());
    }

    @GetMapping("/getUserBadges/{username}")
    public ResponseEntity<Set<BadgeEntity>> getUserBadges(@PathVariable("username") String username) {
        return ResponseEntity.ok(badgeService.getUserBadges(username));
    }


}
