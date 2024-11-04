package com.whisper.controller;

import com.whisper.dto.CreateDisputeRequest;
import com.whisper.dto.DisputeCommentDTO;
import com.whisper.dto.DisputeCommentDeleteRequest;
import com.whisper.dto.DisputeDTO;
import com.whisper.persistence.entity.Dispute;
import com.whisper.persistence.entity.DisputeComment;
import com.whisper.service.DisputeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispute")
@CrossOrigin(origins = "*")
public class DisputeController {

    private final DisputeService disputeService;

    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Dispute> createWhisper(@RequestBody CreateDisputeRequest createDisputeRequest) {
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

}
