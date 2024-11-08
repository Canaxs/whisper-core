package com.whisper.service.impl;

import com.whisper.dto.*;
import com.whisper.persistence.entity.*;
import com.whisper.persistence.repository.*;
import com.whisper.service.DisputeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DisputeServiceImpl implements DisputeService {


    private final DisputeRepository disputeRepository;

    private final DisputeCommentRepository disputeCommentRepository;

    private final DisputeLikeRepository disputeLikeRepository;

    private final WhisperRepository whisperRepository;

    private final DisputeTagRepository disputeTagRepository;

    private final UserRepository userRepository;

    public DisputeServiceImpl(DisputeRepository disputeRepository, DisputeCommentRepository disputeCommentRepository, DisputeLikeRepository disputeLikeRepository, WhisperRepository whisperRepository, DisputeTagRepository disputeTagRepository, UserRepository userRepository) {
        this.disputeRepository = disputeRepository;
        this.disputeCommentRepository = disputeCommentRepository;
        this.disputeLikeRepository = disputeLikeRepository;
        this.whisperRepository = whisperRepository;
        this.disputeTagRepository = disputeTagRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Dispute createDispute(CreateDisputeRequest createDisputeRequest) {
        try {
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            Whisper whisper = whisperRepository.findById(createDisputeRequest.whisperId()).get();
            Dispute dispute = Dispute.builder()
                    .description(createDisputeRequest.description())
                    .whisper(whisper)
                    .user(user)
                    .build();
            disputeRepository.save(dispute);
            DisputeLike disputeLike = new DisputeLike();
            disputeLike.setNumberLike(0);
            disputeLike.setNumberDislike(0);
            disputeLike.setDispute(dispute);
            disputeLikeRepository.save(disputeLike);
            if(!createDisputeRequest.tags().isEmpty()) {
                DisputeTag disputeTag = new DisputeTag();
                disputeTag.setTags(createDisputeRequest.tags());
                disputeTag.setDispute(dispute);
                disputeTagRepository.save(disputeTag);
                dispute.setDisputeTag(disputeTag);
                disputeRepository.save(dispute);
            }

        }
        catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public Dispute getDispute(Long disputeId) {
        return disputeRepository.findById(disputeId).get();
    }

    @Override
    public Dispute updateDispute(Dispute dispute) {
        return disputeRepository.save(dispute);
    }

    @Override
    public Dispute deleteDispute(Long disputeId) {
        Dispute dispute;
        try {
            dispute = disputeRepository.findById(disputeId).get();
            disputeRepository.delete(dispute);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
        return dispute;
    }

    @Override
    public Page<DisputeDTO> getAll(Pageable page) {
        Page<Dispute> disputes = disputeRepository.getAllDispute(page);
        int totalElements = 10;
        Set<String> empty = new HashSet<>();

        return new PageImpl<DisputeDTO>(disputes.getContent()
                .stream()
                .map(dispute -> new DisputeDTO(
                        dispute.getId(),
                        dispute.getDescription(),
                        dispute.getUser(),
                        dispute.getCreatedDate(),
                        dispute.getWhisper().getTitle(),
                        dispute.getWhisper().getUrlName(),
                        dispute.getWhisper().getCategory(),
                        dispute.getWhisper().getSource(),
                        dispute.getWhisper().getAuthorName(),
                        dispute.getWhisper().getImageURL(),
                        dispute.getDisputeTag() == null ? empty : dispute.getDisputeTag().getTags(),
                        dispute.getDisputeComments().size()))
                .collect(Collectors.toList()), page, totalElements);
    }

    @Override
    public String likeDispute(Long disputeId) {
        if(disputeLikeRepository.existsById(disputeId)) {
            DisputeLike disputeLike = disputeLikeRepository.findById(disputeId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(!disputeLike.getLikeUsers().contains(user) && !disputeLike.getDislikeUsers().contains(user)) {
                disputeLike.getLikeUsers().add(user);
                disputeLike.setNumberLike(disputeLike.getNumberLike()+1);
                disputeLikeRepository.save(disputeLike);
                return "Successfully liked";
            }
            else {
                return "User already liked";
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public String dislikeDispute(Long disputeId) {
        if(disputeLikeRepository.existsById(disputeId)) {
            DisputeLike disputeLike = disputeLikeRepository.findById(disputeId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(!disputeLike.getDislikeUsers().contains(user) && !disputeLike.getLikeUsers().contains(user)) {
                disputeLike.getDislikeUsers().add(user);
                disputeLike.setNumberDislike(disputeLike.getNumberDislike()+1);
                disputeLikeRepository.save(disputeLike);
                return "Successfully disliked";
            }
            else {
                return "User already disliked";
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public String unLikeDispute(Long disputeId) {
        if(disputeLikeRepository.existsById(disputeId)) {
            DisputeLike disputeLike = disputeLikeRepository.findById(disputeId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(disputeLike.getLikeUsers().contains(user)) {
                disputeLike.getLikeUsers().remove(user);
                disputeLike.setNumberLike(disputeLike.getNumberLike()-1);
                disputeLikeRepository.save(disputeLike);
                return "Successfully unLiked";
            }
            else {
                return "User already unLiked";
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public String unDislikeDispute(Long disputeId) {
        if(disputeLikeRepository.existsById(disputeId)) {
            DisputeLike disputeLike = disputeLikeRepository.findById(disputeId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(disputeLike.getDislikeUsers().contains(user)) {
                disputeLike.getDislikeUsers().remove(user);
                disputeLike.setNumberDislike(disputeLike.getNumberDislike()-1);
                disputeLikeRepository.save(disputeLike);
                return "Successfully unDisliked";
            }
            else {
                return "User already unDisliked";
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public DisputeComment createComment(DisputeCommentDTO disputeCommentDTO) {
        if(disputeRepository.existsById(disputeCommentDTO.disputeId())) {
            Dispute dispute = disputeRepository.findById(disputeCommentDTO.disputeId()).get();
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            DisputeComment disputeComment = new DisputeComment();
            disputeComment.setComment(disputeCommentDTO.description());
            disputeComment.setDispute(dispute);
            disputeComment.setUser(userRepository.findByUsername(name).get());
            disputeCommentRepository.save(disputeComment);
            dispute.getDisputeComments().add(disputeComment);
            disputeRepository.save(dispute);
            return disputeComment;
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean deleteComment(DisputeCommentDeleteRequest commentDeleteRequest) {
        if(disputeCommentRepository.existsById(commentDeleteRequest.commentId())) {
            DisputeComment disputeComment = disputeCommentRepository.findById(commentDeleteRequest.commentId()).get();
            disputeCommentRepository.delete(disputeComment);
            Dispute dispute = disputeRepository.findById(commentDeleteRequest.disputeId()).get();
            dispute.getDisputeComments().remove(disputeComment);
            return true;
        }
        return false;
    }

    @Override
    public Boolean controlLike(Long disputeId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return disputeLikeRepository.controlLike(disputeId,user);
    }

    @Override
    public Boolean controlDislike(Long disputeId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return disputeLikeRepository.controlDislike(disputeId,user);
    }
}
