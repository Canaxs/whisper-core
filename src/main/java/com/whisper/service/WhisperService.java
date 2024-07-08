package com.whisper.service;

import com.whisper.dto.WhisperRequest;
import com.whisper.persistence.entity.Whisper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WhisperService {

    Whisper createWhisper(WhisperRequest whisperRequest);
    Whisper getWhisper(Long whisperId);
    Whisper updateWhisper(Whisper whisper);
    String deleteWhisper(Long whisperId);
    Page<Whisper> getCategoryWhispers(String categoryName, Pageable page);

    String likeWhisper(Long whisperId);
}
