package com.whisper.service;

import com.whisper.dto.CommentDTO;
import com.whisper.dto.CommentDeleteRequest;
import com.whisper.dto.ViewsUpdateRequest;
import com.whisper.dto.WhisperRequest;
import com.whisper.persistence.entity.Whisper;
import com.whisper.persistence.entity.WhisperComment;
import com.whisper.persistence.entity.WhisperView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WhisperService {

    Whisper createWhisper(WhisperRequest whisperRequest);
    Whisper getWhisper(Long whisperId);
    Whisper updateWhisper(Whisper whisper);
    String deleteWhisper(Long whisperId);
    Page<Whisper> getCategoryWhispers(String categoryName, Pageable page);
    String likeWhisper(Long whisperId);
    WhisperView viewsUpdate(ViewsUpdateRequest viewsUpdate);

    WhisperComment commentCreate(CommentDTO commentDTO);

    Boolean commentDelete(CommentDeleteRequest commentDeleteRequest);
}
