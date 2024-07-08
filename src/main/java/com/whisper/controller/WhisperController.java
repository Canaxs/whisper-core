package com.whisper.controller;

import com.whisper.dto.WhisperRequest;
import com.whisper.persistence.entity.Whisper;
import com.whisper.service.WhisperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/whisper")
@CrossOrigin
public class WhisperController {

    private final WhisperService whisperService;

    public WhisperController(WhisperService whisperService) {
        this.whisperService = whisperService;
    }

    @PostMapping("/create")
    public ResponseEntity<Whisper> createWhisper(@RequestBody WhisperRequest whisperRequest) {
        return ResponseEntity.ok(whisperService.createWhisper(whisperRequest));
    }

    @GetMapping("/{whisperId}")
    public ResponseEntity<Whisper> getWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.getWhisper(whisperId));
    }

    @PutMapping("/update")
    public ResponseEntity<Whisper> updateWhisper(@RequestBody Whisper whisper) {
        return ResponseEntity.ok(whisperService.updateWhisper(whisper));
    }

    @DeleteMapping("/delete/{whisperId}")
    public ResponseEntity<String> deleteWhisper(@PathVariable("whisperId") Long whisperId) {
        return ResponseEntity.ok(whisperService.deleteWhisper(whisperId));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<Whisper>> getCategoryWhispers(@PathVariable("categoryName") String categoryName, Pageable page) {
        return ResponseEntity.ok(whisperService.getCategoryWhispers(categoryName, page));
    }
}
