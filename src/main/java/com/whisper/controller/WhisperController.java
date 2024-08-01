package com.whisper.controller;

import com.whisper.dto.*;
import com.whisper.persistence.entity.Whisper;
import com.whisper.persistence.entity.WhisperComment;
import com.whisper.persistence.entity.WhisperView;
import com.whisper.service.WhisperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Whisper> createWhisper(@RequestBody WhisperRequest whisperRequest) {
        return ResponseEntity.ok(whisperService.createWhisper(whisperRequest));
    }

    @GetMapping("/{whisperId}")
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

    @GetMapping("/carousel/big")
    public ResponseEntity<List<WhisperDTO>> getCarouselBig() {
        return ResponseEntity.ok(whisperService.getCarouselBig());
    }

    @GetMapping("/carousel/small")
    public ResponseEntity<List<WhisperDTO>> getCarouselSmall() {
        return ResponseEntity.ok(whisperService.getCarouselSmall());
    }

}
