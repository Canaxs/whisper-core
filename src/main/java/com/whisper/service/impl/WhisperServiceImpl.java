package com.whisper.service.impl;

import com.whisper.dto.ViewsUpdateRequest;
import com.whisper.dto.WhisperRequest;
import com.whisper.enums.Category;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.entity.Whisper;
import com.whisper.persistence.entity.WhisperLike;
import com.whisper.persistence.entity.WhisperView;
import com.whisper.persistence.repository.UserRepository;
import com.whisper.persistence.repository.WhisperLikeRepository;
import com.whisper.persistence.repository.WhisperRepository;
import com.whisper.persistence.repository.WhisperViewRepository;
import com.whisper.service.WhisperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class WhisperServiceImpl implements WhisperService {

    private final WhisperRepository whisperRepository;
    private final WhisperLikeRepository whisperLikeRepository;
    private final WhisperViewRepository whisperViewRepository;
    private final UserRepository userRepository;


    public WhisperServiceImpl(WhisperRepository whisperRepository, WhisperLikeRepository whisperLikeRepository, WhisperViewRepository whisperViewRepository, UserRepository userRepository) {
        this.whisperRepository = whisperRepository;
        this.whisperLikeRepository = whisperLikeRepository;
        this.whisperViewRepository = whisperViewRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Whisper createWhisper(WhisperRequest whisperRequest) {
        Whisper whisper = Whisper.builder()
                .authorName(whisperRequest.authorName())
                .title(whisperRequest.title())
                .image(whisperRequest.image())
                .description(whisperRequest.description())
                .source(whisperRequest.source())
                .isActive(false)
                .isDelete(false)
                .category(Category.convert(whisperRequest.category().toUpperCase()))
                .build();
        try {
            WhisperLike whisperLike = new WhisperLike();
            whisperLike.setNumberLike(0);
            whisperLike.setNumberDislike(0);
            whisperLike.setWhisper(whisper);
            whisperLikeRepository.save(whisperLike);
            WhisperView whisperView = new WhisperView();
            whisperView.setWhisper(whisper);
            whisperView.setNumberOfViews(0L);
            whisperViewRepository.save(whisperView);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
        return whisperRepository.save(whisper);
    }

    @Override
    public Whisper getWhisper(Long whisperId) {
        return whisperRepository.getReferenceById(whisperId);
    }

    @Override
    public Whisper updateWhisper(Whisper whisper) {
        return whisperRepository.save(whisper);
    }

    @Override
    public String deleteWhisper(Long whisperId) {
        try {
            Whisper whisper = whisperRepository.getReferenceById(whisperId);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if(Objects.equals(whisper.getAuthorName(), name)) {
                whisperRepository.delete(whisper);
                return "Succesfuly Deleted";
            }
            else {
                throw new RuntimeException();
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Page<Whisper> getCategoryWhispers(String categoryName, Pageable page) {
        return whisperRepository.findByCategory(Category.convert(categoryName) , page);
    }

    @Override
    public String likeWhisper(Long whisperId) {
        if(whisperLikeRepository.existsById(whisperId)) {
            WhisperLike whisperLike = whisperLikeRepository.getReferenceById(whisperId);
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(!whisperLike.getUsers().contains(user)) {
                Set<User> users = whisperLike.getUsers();
                users.add(user);
                whisperLike.setUsers(users);
                whisperLike.setNumberLike(whisperLike.getNumberLike()+1);
                whisperLikeRepository.save(whisperLike);
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
    public WhisperView viewsUpdate(ViewsUpdateRequest viewsUpdate) {
        if(whisperViewRepository.existsById(viewsUpdate.whisperId())) {
            WhisperView whisperView = whisperViewRepository.getReferenceById(viewsUpdate.whisperId());
            whisperView.setNumberOfViews(viewsUpdate.numberOfViews());
            whisperViewRepository.save(whisperView);
            return whisperView;
        }
        else {
            throw new RuntimeException();
        }
    }
}
