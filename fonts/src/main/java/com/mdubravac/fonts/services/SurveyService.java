package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.entities.Impairment;
import com.mdubravac.fonts.entities.Survey;
import com.mdubravac.fonts.mappers.SurveyMapper;
import com.mdubravac.fonts.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    private final SurveyMapper surveyMapper;

    public Long submit(SurveyDto surveyDto) {
        Survey survey = surveyMapper.toSurvey(surveyDto);
        surveyRepository.save(survey);
        return survey.getId();
    }
}
