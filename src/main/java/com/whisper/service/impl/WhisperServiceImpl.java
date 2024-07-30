package com.whisper.service.impl;

import com.whisper.dto.*;
import com.whisper.enums.Category;
import com.whisper.persistence.entity.*;
import com.whisper.persistence.repository.*;
import com.whisper.service.WhisperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WhisperServiceImpl implements WhisperService {

    private final WhisperRepository whisperRepository;
    private final WhisperLikeRepository whisperLikeRepository;
    private final WhisperViewRepository whisperViewRepository;
    private final UserRepository userRepository;
    private final WhisperCommentRepository whisperCommentRepository;


    public WhisperServiceImpl(WhisperRepository whisperRepository, WhisperLikeRepository whisperLikeRepository, WhisperViewRepository whisperViewRepository, UserRepository userRepository, WhisperCommentRepository whisperCommentRepository) {
        this.whisperRepository = whisperRepository;
        this.whisperLikeRepository = whisperLikeRepository;
        this.whisperViewRepository = whisperViewRepository;
        this.userRepository = userRepository;
        this.whisperCommentRepository = whisperCommentRepository;
    }


    @Override
    public Whisper createWhisper(WhisperRequest whisperRequest) {
        Whisper whisper = Whisper.builder()
                .authorName(SecurityContextHolder.getContext().getAuthentication().getName())
                .title(whisperRequest.title())
                .image(whisperRequest.image())
                .description(whisperRequest.description())
                .source(whisperRequest.source())
                .isActive(false)
                .isDelete(false)
                .category(Category.convert(whisperRequest.category().toUpperCase()))
                .build();
        whisper.setUrlName(createUrlName(whisper.getTitle()));
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
                whisperLike.getUsers().add(user);
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

    @Override
    public WhisperComment commentCreate(CommentDTO commentDTO) {
        if(whisperRepository.existsById(commentDTO.whisperId())) {
            Whisper whisper = whisperRepository.findById(commentDTO.whisperId()).get();
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            WhisperComment whisperComment = new WhisperComment();
            whisperComment.setComment(commentDTO.comment());
            whisperComment.setWhisper(whisper);
            whisperComment.setUser(userRepository.findByUsername(name).get());
            whisperCommentRepository.save(whisperComment);
            whisper.getWhisperComment().add(whisperComment);
            whisperRepository.save(whisper);
        }
        else {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public Boolean commentDelete(CommentDeleteRequest commentDeleteRequest) {
        if(whisperCommentRepository.existsById(commentDeleteRequest.commentId())) {
            WhisperComment whisperComment = whisperCommentRepository.findById(commentDeleteRequest.commentId()).get();
            whisperCommentRepository.delete(whisperComment);
            Whisper whisper = whisperRepository.findById(commentDeleteRequest.whisperId()).get();
            whisper.getWhisperComment().remove(whisperComment);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getCategoryName() {
        List<String> strings = new ArrayList<>();
        for (Category category : Category.values()) {
            strings.add(Category.convertTR(category));
        }
        return strings;
    }

    @Override
    public String createUrlName(String title) {
        StringBuilder createUrlName = new StringBuilder();
        title = title.toLowerCase();
        title = turkishToEnglish(title);
        String[] titleList = title.split(" ");

        for(String word : titleList) {
            createUrlName.append(word);
            createUrlName.append("-");
        }
        createUrlName.append(whisperRepository.getByIdNumber()+1);

        return createUrlName.toString();
    }

    @Override
    public WhisperDTO getUrlNameWhisper(String urlName) {
        Whisper whisper = whisperRepository.findByUrlName(urlName);
        if(!whisper.getIsActive()) {
            //throw new RuntimeException();
        }
        return WhisperDTO.builder()
                .authorName(whisper.getAuthorName())
                .title(whisper.getTitle())
                .source(whisper.getSource())
                .image(whisper.getImage())
                .category(whisper.getCategory())
                .createdDate(whisper.getCreatedDate())
                .build();

    }

    @Override
    public List<WhisperPanelDTO> getWhispers() {
        return whisperRepository.getAllByWhispers();
    }

    @Override
    public List<WhisperPanelDTO> getPendingWhispers() {
        return whisperRepository.getAllByPendingWhispers();
    }

    @Override
    public List<WhisperDTO> getBestUserPoint() {
        return whisperRepository.getBestUserPoint();
    }

    private String turkishToEnglish(String title) {
        return title.replace('Ğ','g')
                .replace('Ü','u')
                .replace('Ş','s')
                .replace('I','i')
                .replace('İ','i')
                .replace('Ö','o')
                .replace('Ç','c')
                .replace('ğ','g')
                .replace('ü','u')
                .replace('ş','s')
                .replace('ı','i')
                .replace('ö','o')
                .replace('ç','c');
    }
}
