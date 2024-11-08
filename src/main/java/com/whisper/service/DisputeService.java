package com.whisper.service;

import com.whisper.dto.CreateDisputeRequest;
import com.whisper.dto.DisputeCommentDTO;
import com.whisper.dto.DisputeCommentDeleteRequest;
import com.whisper.dto.DisputeDTO;
import com.whisper.persistence.entity.Dispute;
import com.whisper.persistence.entity.DisputeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
