package com.whisper.dto;

import com.whisper.enums.Category;
import com.whisper.persistence.entity.DisputeTag;
import com.whisper.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDisputeDTO {

    private Long id;

    private String description;
    private User user;
    private Date createdDate;

    private String whisperTitle;
    private String whisperUrlName;
    private Category whisperCategory;
    private String whisperSource;
    private String whisperAuthorName;
    private String whisperImageURL;

    private DisputeTag disputeTag;

    private Integer commentSize;
}
