package com.whisper.controller;

import com.whisper.dto.*;
import com.whisper.persistence.entity.Whisper;
import com.whisper.persistence.entity.WhisperComment;
import com.whisper.persistence.entity.WhisperView;
import com.whisper.service.WhisperService;
import com.whisper.specification.WhisperFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/whisper")
@CrossOrigin(origins = "*")
public class WhisperController {

    private final WhisperService whisperService;

    public WhisperController(WhisperService whisperService) {
        this.whisperService = whisperService;
    }

    @PostMapping("/locked/create")
    public ResponseEntity<Whisper> createWhisper(@RequestPart("whisperRequest") WhisperRequest whisperRequest,
                                                 @RequestPart("image") MultipartFile imageFile) {
        return ResponseEntity.ok(whisperService.createWhisper(whisperRequest , imageFile));
    }

    @GetMapping("/getId/{whisperId}")
    public ResponseEntity<Whisper> getWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.getWhisper(whisperId));
    }

    @PutMapping("/locked/update")
    public ResponseEntity<Whisper> updateWhisper(@RequestBody Whisper whisper) {
        return ResponseEntity.ok(whisperService.updateWhisper(whisper));
    }

    @DeleteMapping("/locked/delete/{whisperId}")
    public ResponseEntity<String> deleteWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.deleteWhisper(whisperId));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<Whisper>> getCategoryWhispers(@PathVariable("categoryName") String categoryName, Pageable page) {
        return ResponseEntity.ok(whisperService.getCategoryWhispers(categoryName, page));
    }

    @GetMapping("/locked/like/{whisperId}")
    public ResponseEntity<String> likeWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.likeWhisper(whisperId));
    }

    @GetMapping("/locked/dislike/{whisperId}")
    public ResponseEntity<String> dislikeWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.dislikeWhisper(whisperId));
    }

    @GetMapping("/locked/unlike/{whisperId}")
    public ResponseEntity<String> unLikeWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.unLikeWhisper(whisperId));
    }

    @GetMapping("/locked/undislike/{whisperId}")
    public ResponseEntity<String> unDislikeWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.unDislikeWhisper(whisperId));
    }

    @PostMapping("/locked/viewsUpdate")
    public ResponseEntity<WhisperView> viewsUpdate(@RequestBody ViewsUpdateRequest viewsUpdate) {
        return ResponseEntity.ok(whisperService.viewsUpdate(viewsUpdate));
    }

    @PostMapping("/locked/comment/create")
    public ResponseEntity<WhisperComment> commentCreate(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(whisperService.commentCreate(commentDTO));
    }

    @DeleteMapping("/locked/comment/delete")
    public ResponseEntity<Boolean> commentDelete(@RequestBody CommentDeleteRequest commentDeleteRequest) {
        return ResponseEntity.ok(whisperService.commentDelete(commentDeleteRequest));
    }

    @GetMapping("/categoryNames")
    public ResponseEntity<List<String>> getCategoryNames() {
        return ResponseEntity.ok(whisperService.getCategoryName());
    }

    @GetMapping("/getUrlName/{urlName}")
    public ResponseEntity<UrlWhisperDTO> getUrlNameWhisper(@PathVariable("urlName") String urlName) {
        return ResponseEntity.ok(whisperService.getUrlNameWhisper(urlName));
    }

    @GetMapping("/getWhispers")
    public ResponseEntity<List<WhisperPanelDTO>> getWhispers() {
        return ResponseEntity.ok(whisperService.getWhispers());
    }

    @GetMapping("/getPendingWhispers")
    public ResponseEntity<List<WhisperPanelDTO>> getPendingWhispers() {
        return ResponseEntity.ok(whisperService.getPendingWhispers());
    }

    @GetMapping("/getBestUserPoint")
    public ResponseEntity<List<WhisperDTO>> getBestUserPoint() {
        return ResponseEntity.ok(whisperService.getBestUserPoint());
    }

    @GetMapping("/getUserWhispers/{username}")
    public ResponseEntity<List<WhisperDTO>> getUserWhispers(@PathVariable("username") String username) {
        return ResponseEntity.ok(whisperService.getUserWhispers(username));
    }

    @PostMapping("/getWhispersFilter")
    public ResponseEntity<Page<Whisper>> getWhispersFilter(@RequestBody WhisperFilter whisperFilter , Pageable page) {
        return ResponseEntity.ok(whisperService.getWhispersFilter(whisperFilter, page));
    }

    @GetMapping("/carousel/big")
    public ResponseEntity<List<WhisperDTO>> getCarouselBig() {
        return ResponseEntity.ok(whisperService.getCarouselBig());
    }

    @GetMapping("/carousel/small")
    public ResponseEntity<List<WhisperDTO>> getCarouselSmall() {
        return ResponseEntity.ok(whisperService.getCarouselSmall());
    }

    @GetMapping("/control/like/{whisperId}")
    public ResponseEntity<Boolean> controlLike(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.controlLike(whisperId));
    }

    @GetMapping("/control/dislike/{whisperId}")
    public ResponseEntity<Boolean> controlDislike(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.controlDislike(whisperId));
    }

    @GetMapping("/updateActive/{whisperId}")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<Boolean> updateIsActive(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.updateIsActive(whisperId));
    }

    @GetMapping("/updateDelete/{whisperId}")
    @PreAuthorize("hasRole('ROLE_MOD')")
    public ResponseEntity<Boolean> updateIsDelete(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.updateIsDelete(whisperId));
    }

}
