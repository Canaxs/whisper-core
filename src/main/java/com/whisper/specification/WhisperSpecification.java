package com.whisper.specification;

import com.whisper.persistence.entity.Whisper;
import org.springframework.data.jpa.domain.Specification;

public class WhisperSpecification {

    public static final String TITLE = "title";

    public static Specification<Whisper> filterBy(WhisperFilter whisperFilter) {
        return Specification.where(hasTitle(whisperFilter.title()));
    }

    private static Specification<Whisper> hasTitle(String title) {
        return ((root, query, cb) -> title == null || title.isEmpty() ? cb.conjunction() : cb.like(root.get(TITLE), "%"+title+"%"));
    }
}
