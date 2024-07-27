package com.whisper.service;

import com.whisper.dto.*;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.Whisper;
import com.whisper.persistence.entity.WhisperComment;
import com.whisper.persistence.entity.WhisperView;
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
    WhisperView viewsUpdate(ViewsUpdateRequest viewsUpdate);

    WhisperComment commentCreate(CommentDTO commentDTO);

    Boolean commentDelete(CommentDeleteRequest commentDeleteRequest);

    List<String> getCategoryName();

    String createUrlName(String title);

    WhisperDTO getUrlNameWhisper(String urlName);

    List<WhisperPanelDTO> getWhispers();

    List<WhisperPanelDTO> getPendingWhispers();
}
