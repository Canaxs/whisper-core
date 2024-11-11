package com.whisper.service;

import com.whisper.dto.*;
import com.whisper.persistence.entity.Dispute;
import com.whisper.persistence.entity.DisputeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DisputeService {

    Dispute createDispute(CreateDisputeRequest createDisputeRequest);

    Dispute getDispute(Long disputeId);

    Dispute updateDispute(Dispute dispute);

    Dispute deleteDispute(Long disputeId);

    Page<DisputeDTO> getAll(Pageable page);

    String likeDispute(Long disputeId);

    String dislikeDispute(Long disputeId);

    String unLikeDispute(Long disputeId);

    String unDislikeDispute(Long disputeId);

    DisputeComment createComment(DisputeCommentDTO disputeCommentDTO);

    Boolean deleteComment(DisputeCommentDeleteRequest commentDeleteRequest);

    Boolean controlLike(Long disputeId);

    Boolean controlDislike(Long disputeId);

    List<TagsDTO> getMostUsedTags();

    List<GetDisputeDTO> getDisputeTag(String disputeTag);
}
