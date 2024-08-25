package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.ParticipantSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantSurveyRepository extends JpaRepository<ParticipantSurvey, Long> {
    ParticipantSurvey findByUuid(String uuid);
}