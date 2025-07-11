package com.whisper.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.dto.*;
import com.whisper.enums.Category;
import com.whisper.persistence.entity.*;
import com.whisper.persistence.repository.*;
import com.whisper.service.NotificationService;
import com.whisper.service.WhisperService;
import com.whisper.specification.WhisperFilter;
import com.whisper.specification.WhisperSpecification;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WhisperServiceImpl implements WhisperService {

    private final WhisperRepository whisperRepository;
    private final WhisperLikeRepository whisperLikeRepository;
    private final WhisperViewRepository whisperViewRepository;
    private final UserRepository userRepository;
    private final WhisperCommentRepository whisperCommentRepository;

    private final NotificationService notificationService;

    private final UserAndPointRepository userAndPointRepository;


    @Value("${imgbb.key}")
    private String imgBBKey;

    @Value("${imgbb.uploadURL}")
    private String imgBBUploadURL;


    public WhisperServiceImpl(WhisperRepository whisperRepository, WhisperLikeRepository whisperLikeRepository, WhisperViewRepository whisperViewRepository, UserRepository userRepository, WhisperCommentRepository whisperCommentRepository, NotificationService notificationService, UserAndPointRepository userAndPointRepository) {
        this.whisperRepository = whisperRepository;
        this.whisperLikeRepository = whisperLikeRepository;
        this.whisperViewRepository = whisperViewRepository;
        this.userRepository = userRepository;
        this.whisperCommentRepository = whisperCommentRepository;
        this.notificationService = notificationService;
        this.userAndPointRepository = userAndPointRepository;
    }


    @Override
    public Whisper createWhisper(WhisperRequest whisperRequest, MultipartFile imageFile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User securityUser = userRepository.findByUsername(username).get();
        if(securityUser.getSubscription().getWriteLimit() != 0) {
            String imageURL = null;
            if (!imageFile.isEmpty()) {
                imageURL = uploadImgBB(imageFile);
            }
            Whisper whisper = Whisper.builder()
                    .authorName(username)
                    .title(whisperRequest.title())
                    .imageURL(imageURL)
                    .description(whisperRequest.description())
                    .source(whisperRequest.source())
                    .isActive(false)
                    .isDelete(false)
                    .category(Category.convert(whisperRequest.category().toUpperCase()))
                    .build();
            whisper.setUrlName(createUrlName(whisper.getTitle()));
            try {
                WhisperLike whisperLike = new WhisperLike();
                whisperLike.setNumberLike((double) 0);
                whisperLike.setNumberDislike((double) 0);
                whisperLike.setWhisper(whisper);
                whisperLikeRepository.save(whisperLike);
                WhisperView whisperView = new WhisperView();
                whisperView.setWhisper(whisper);
                whisperView.setNumberOfViews(0L);
                whisperViewRepository.save(whisperView);

            } catch (Exception e) {
                throw new RuntimeException();
            }
            return whisperRepository.save(whisper);
        }
        else {
            return null;
        }
    }

    @Override
    public Whisper getWhisper(Long whisperId) {
        return whisperRepository.findById(whisperId).get();
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
        if(whisperRepository.existsById(whisperId)) {
            Whisper whisper = whisperRepository.findById(whisperId).get();
            WhisperLike whisperLike = whisperLikeRepository.findById(whisper.getWhisperLike().getId()).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(!whisperLike.getLikeUsers().contains(user) && !whisperLike.getDislikeUsers().contains(user)) {
                whisperLike.getLikeUsers().add(user);
                whisperLike.setNumberLike(whisperLike.getNumberLike()+1);
                //
                UserAndPoint userAndPoint = new UserAndPoint();
                userAndPoint.setUser(user);
                User author = userRepository.findByUsername(whisper.getAuthorName()).get();
                userAndPoint.setWhisper(whisper);
                Double scorePoint = scoreCalculation(author,whisper);
                userAndPoint.setPoint(scorePoint);
                userAndPointRepository.save(userAndPoint);
                //
                author.setUserPoint(author.getUserPoint()+scorePoint);
                userRepository.save(author);
                //
                whisperLike.getUserPoints().add(userAndPoint);
                //
                whisperLikeRepository.save(whisperLike);

                String actionInfo = "Paylaşımınızı beğendi";
                notificationService.create(securityName,actionInfo,author);

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
    public String dislikeWhisper(Long whisperId) {
        if(whisperLikeRepository.existsById(whisperId)) {
            WhisperLike whisperLike = whisperLikeRepository.getReferenceById(whisperId);
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(!whisperLike.getDislikeUsers().contains(user) && !whisperLike.getLikeUsers().contains(user)) {
                whisperLike.getDislikeUsers().add(user);
                whisperLike.setNumberDislike(whisperLike.getNumberDislike()+1);
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
    public String unLikeWhisper(Long whisperId) {
        if(whisperLikeRepository.existsById(whisperId)) {
            WhisperLike whisperLike = whisperLikeRepository.getReferenceById(whisperId);
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(whisperLike.getLikeUsers().contains(user)) {
                whisperLike.getLikeUsers().remove(user);
                whisperLike.setNumberLike(whisperLike.getNumberLike()-1);

                Whisper whisper = whisperRepository.findById(whisperId).get();
                UserAndPoint userAndPoint = userAndPointRepository.getUserAndPointByWhisperAndUser(user,whisper);

                User author = userRepository.findByUsername(whisper.getAuthorName()).get();
                author.setUserPoint(author.getUserPoint() - userAndPoint.getPoint());
                userRepository.save(user);

                whisperLike.getUserPoints().remove(userAndPoint);

                whisperLikeRepository.save(whisperLike);
                userAndPointRepository.delete(userAndPoint);

                return "Successfully unliked";
            }
            else {
                return "User already unliked";
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public String unDislikeWhisper(Long whisperId) {
        if(whisperLikeRepository.existsById(whisperId)) {
            WhisperLike whisperLike = whisperLikeRepository.getReferenceById(whisperId);
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(securityName).get();
            if(whisperLike.getDislikeUsers().contains(user)) {
                whisperLike.getDislikeUsers().remove(user);
                whisperLike.setNumberDislike(whisperLike.getNumberDislike()-1);
                whisperLikeRepository.save(whisperLike);
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
            User whisperUser = userRepository.findByUsername(name).get();
            WhisperComment whisperComment = new WhisperComment();
            whisperComment.setComment(commentDTO.comment());
            whisperComment.setWhisper(whisper);
            whisperComment.setUser(whisperUser);
            whisperCommentRepository.save(whisperComment);
            whisper.getWhisperComment().add(whisperComment);
            whisperRepository.save(whisper);

            String actionInfo = "Paylaşımınıza Yorum Yaptı.";
            notificationService.create(name,actionInfo,whisperUser);
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
        title = title.replaceAll("\\p{Punct}", "");
        title = title.toLowerCase();
        title = turkishToEnglish(title);
        String[] titleList = title.split(" ");

        for(String word : titleList) {
            createUrlName.append(word);
            createUrlName.append("-");
        }
        Long id = whisperRepository.getByIdNumber();
        if(id == null) {
            id = 0L;
        }
        createUrlName.append(id+1);

        return createUrlName.toString();
    }

    @Override
    public UrlWhisperDTO getUrlNameWhisper(String urlName) {
        Whisper whisper = whisperRepository.findByUrlName(urlName);
        if(!whisper.getIsActive()) {
            //throw new RuntimeException();
        }
        return UrlWhisperDTO.builder()
        		.Id(whisper.getId())
                .authorName(whisper.getAuthorName())
                .title(whisper.getTitle())
                .description(whisper.getDescription())
                .source(whisper.getSource())
                .imageURL(whisper.getImageURL())
                .category(Category.convertTR(whisper.getCategory()))
                .createdDate(whisper.getCreatedDate())
                .whisperLike(whisper.getWhisperLike())
                .whisperComment(whisper.getWhisperComment())
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
    public Page<WhisperDTO> getBestUserPoint(Pageable page) {
        return whisperRepository.getBestUserPoint(page);
    }

    @Override
    public List<WhisperDTO> getUserWhispers(String username) {
        return whisperRepository.getUserWhispers(username);
    }

    @Override
    public List<WhisperDTO> getCarouselBig() {
        Pageable topTen = PageRequest.of(0, 10);
        return whisperRepository.getTop10Carousel(topTen);
    }

    @Override
    public List<WhisperDTO> getCarouselSmall() {
        return whisperRepository.getCarouselSmall();
    }

    @Override
    public Boolean controlLike(Long whisperId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return whisperLikeRepository.controlLike(whisperId,user);
    }

    @Override
    public Boolean controlDislike(Long whisperId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return whisperLikeRepository.controlDislike(whisperId,user);
    }

    @Override
    public Boolean updateIsActive(Long whisperId) {
        Whisper whisper;
        try {
            whisper = whisperRepository.findById(whisperId).get();
            whisper.setIsActive(true);
            whisperRepository.save(whisper);

            String actionInfo = "Başlıklı paylaşımınız onaylandı";
            notificationService.create(whisper.getTitle(),actionInfo,userRepository.findByUsername(whisper.getAuthorName()).get());
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean updateIsDelete(Long whisperId) {
        Whisper whisper;
        try {
            whisper = whisperRepository.findById(whisperId).get();
            whisper.setIsDelete(true);
            whisperRepository.save(whisper);

            String actionInfo = "Başlıklı paylaşımınız onaylanmadı.Tekrar deneyiniz.";
            notificationService.create(whisper.getTitle(),actionInfo,userRepository.findByUsername(whisper.getAuthorName()).get());
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Page<Whisper> getWhispersFilter(WhisperFilter whisperFilter, Pageable page) {
        Specification<Whisper> spec = WhisperSpecification.filterBy(whisperFilter);
        return whisperRepository.findAll(spec, page);
    }

    @Override
    public String uploadImgBB(MultipartFile imageFile) {
        String imgBBUrl = imgBBUploadURL + "?key=" + imgBBKey;

        try {
            RestTemplate restTemplate = new RestTemplate();
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

            byte[] fileContent = compressImageToWebP(originalImage, 600L);

            String encodedString = java.util.Base64.getEncoder().encodeToString(fileContent);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("image", encodedString);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(imgBBUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getBody());
                return jsonNode.path("data").path("url").asText();
            } else {
                throw new RuntimeException("imgBB upload failed with status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }


    @Override
    public byte[] compressImageToWebP(BufferedImage image, long targetSizeKB) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No WebP writer found");
        }

        ImageWriter writer = writers.next();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

        String[] compressionTypes = writeParam.getCompressionTypes();
        if (compressionTypes != null && compressionTypes.length > 0) {
            writeParam.setCompressionType(compressionTypes[0]);
        }

        float quality = 1.0f;
        while (quality > 0) {
            baos.reset();
            writeParam.setCompressionQuality(quality);

            writer.write(null, new IIOImage(image, null, null), writeParam);
            if (baos.size() / 1024 <= targetSizeKB) {
                break;
            }
            quality -= 0.05f;
        }

        writer.dispose();
        ios.close();
        return baos.toByteArray();
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

    private Double scoreCalculation(User user, Whisper whisper) {
        Integer whisperCount = whisperRepository.getByWhisperCount(user.getUsername());
        Integer whisperLikeCount = whisperRepository.getByWhispersLikeCount(user.getUsername());
        double s1,s2,s3;
        // s1
        if (whisperCount <= 5) {
            s1 = 9;
        } else if (whisperCount <= 10) {
            s1 = 8;
        } else if (whisperCount <= 15) {
            s1 = 7;
        } else if (whisperCount <= 20) {
            s1 = 6;
        } else if (whisperCount <= 25) {
            s1 = 5;
        } else if (whisperCount <= 35) {
            s1 = 4;
        } else if (whisperCount <= 45) {
            s1 = 3;
        } else if (whisperCount <= 60) {
            s1 = 2;
        } else {
            s1 = 1;
        }

        // s2

        if (whisperLikeCount <= 50) {
            s2 = 9;
        } else if (whisperLikeCount <= 100) {
            s2 = 8;
        } else if (whisperLikeCount <= 200) {
            s2 = 7;
        } else if (whisperLikeCount <= 300) {
            s2 = 6;
        } else if (whisperLikeCount <= 400) {
            s2 = 5;
        } else if (whisperLikeCount <= 800) {
            s2 = 4;
        } else if (whisperLikeCount <= 1000) {
            s2 = 3;
        } else if (whisperLikeCount <= 2000) {
            s2 = 2;
        } else {
            s2 = 1;
        }

        // s3
        Calendar nowDate = Calendar.getInstance();
        Calendar whisperDate = Calendar.getInstance();
        whisperDate.setTime(whisper.getCreatedDate());
        whisperDate.add(Calendar.HOUR, 1);

        
        if (nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
            s3 = 9;
        }
        else {
            whisperDate.add(Calendar.HOUR, 2);
            if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                s3 = 8;
            }
            else {
                whisperDate.add(Calendar.HOUR, 5);
                if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                    s3 = 7;
                }
                else {
                    whisperDate.add(Calendar.DATE, 1);
                    if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                        s3 = 6;
                    }
                    else {
                        whisperDate.add(Calendar.DATE, 2);
                        if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                            s3 = 5;
                        }
                        else {
                            whisperDate.add(Calendar.DATE, 3);
                            if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                                s3 = 4;
                            }
                            else {
                                whisperDate.add(Calendar.DATE, 3);
                                if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                                    s3 = 3;
                                }
                                else {
                                    whisperDate.add(Calendar.DATE, 15);
                                    if(nowDate.getTime().compareTo(whisperDate.getTime()) < 0) {
                                        s3 = 2;
                                    }
                                    else {
                                        s3 = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double total = 0;
        double exclusive = 0;

        if(user.getSubscription().getExclusive()) {
            exclusive+=3;
        }

        total = ( (s1+s2+s3) + exclusive ) / 3;

        return total;
    }
}
