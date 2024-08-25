package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.SurveyText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyTextRepository extends JpaRepository<SurveyText, Long> {
    Long countByCollectionId(Long collectionId);

    Optional<SurveyText> findByLocalIdAndCollectionId(Long localId, Long collectionId);
}
