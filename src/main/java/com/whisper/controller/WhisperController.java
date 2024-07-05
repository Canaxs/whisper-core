package com.whisper.controller;

import com.whisper.dto.WhisperRequest;
import com.whisper.persistence.entity.Whisper;
import com.whisper.service.WhisperService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whisper")
@CrossOrigin
public class WhisperController {

    private final WhisperService whisperService;

    public WhisperController(WhisperService whisperService) {
        this.whisperService = whisperService;
    }

    @PostMapping("/create")
    public Whisper createWhisper(@RequestBody WhisperRequest whisperRequest) {
        return null;
    }

    @GetMapping("/{whisperId}")
    public Whisper getWhisper(@PathVariable("whisperId") Long whisperId) {
        return null;
    }

    @PutMapping("/update")
    public Whisper updateWhisper(@RequestBody Whisper whisper) {
        return null;
    }

    @DeleteMapping("/delete/{whisperId}")
    public String deleteWhisper(@PathVariable("whisperId") Long whisperId) {
        return null;
    }
}
