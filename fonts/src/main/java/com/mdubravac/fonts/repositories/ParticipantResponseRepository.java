package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.ParticipantResponse;
import com.mdubravac.fonts.entities.ParticipantSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantResponseRepository extends JpaRepository<ParticipantResponse, Long> {
    List<ParticipantResponse> findBySurvey(ParticipantSurvey survey);
}
