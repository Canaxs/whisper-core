package com.whisper.controller;

import com.whisper.dto.*;
import com.whisper.persistence.entity.Dispute;
import com.whisper.persistence.entity.DisputeComment;
import com.whisper.service.DisputeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dispute")
@CrossOrigin(origins = "*")
public class DisputeController {

    private final DisputeService disputeService;

    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Dispute> createDispute(@RequestBody CreateDisputeRequest createDisputeRequest) {
        return ResponseEntity.ok(disputeService.createDispute(createDisputeRequest));
    }

    @GetMapping("/getDispute/{disputeId}")
    public ResponseEntity<Dispute> getDispute(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.getDispute(disputeId));
    }

    @PutMapping("/updateDispute")
    public ResponseEntity<Dispute> updateDispute(@RequestBody Dispute dispute) {
        return ResponseEntity.ok(disputeService.updateDispute(dispute));
    }

    @DeleteMapping("/deleteDispute/{disputeId}")
    public ResponseEntity<Dispute> deleteDispute(@PathVariable Long disputeId) {
        return ResponseEntity.ok(disputeService.deleteDispute(disputeId));
    }

    @GetMapping("/like/{disputeId}")
    public ResponseEntity<String> likeDispute(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.likeDispute(disputeId));
    }

    @GetMapping("/dislike/{disputeId}")
    public ResponseEntity<String> dislikeDispute(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.dislikeDispute(disputeId));
    }

    @GetMapping("/unlike/{disputeId}")
    public ResponseEntity<String> unLikeDispute(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.unLikeDispute(disputeId));
    }

    @GetMapping("/undislike/{disputeId}")
    public ResponseEntity<String> unDislikeDispute(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.unDislikeDispute(disputeId));
    }

    @PostMapping("/getAll")
    public ResponseEntity<Page<DisputeDTO>> getAll(Pageable page) {
        return ResponseEntity.ok(disputeService.getAll(page));
    }

    @PostMapping("/createComment")
    public ResponseEntity<DisputeComment> createComment(@RequestBody DisputeCommentDTO disputeCommentDTO) {
        return ResponseEntity.ok(disputeService.createComment(disputeCommentDTO));
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<Boolean> deleteComment(@RequestBody DisputeCommentDeleteRequest disputeCommentDeleteRequest) {
        return ResponseEntity.ok(disputeService.deleteComment(disputeCommentDeleteRequest));
    }

    @GetMapping("/control/like/{disputeId}")
    public ResponseEntity<Boolean> controlLike(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.controlLike(disputeId));
    }

    @GetMapping("/control/dislike/{disputeId}")
    public ResponseEntity<Boolean> controlDislike(@PathVariable("disputeId") Long disputeId) {
        return ResponseEntity.ok(disputeService.controlDislike(disputeId));
    }

    @GetMapping("/getMostUsedTags")
    public ResponseEntity<List<TagsDTO>> getMostUsedTags() {
        return ResponseEntity.ok(disputeService.getMostUsedTags());
    }

    @GetMapping("/getDisputeTag/{disputeTag}")
    public ResponseEntity<List<GetDisputeDTO>> getDisputeTag(@PathVariable("disputeTag") String disputeTag) {
        return ResponseEntity.ok(disputeService.getDisputeTag(disputeTag));
    }

}
