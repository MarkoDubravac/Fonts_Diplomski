package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.entities.Survey;
import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.mappers.SurveyMapper;
import com.mdubravac.fonts.repositories.SurveyRepository;
import com.mdubravac.fonts.repositories.SurveyTextRepository;
import com.mdubravac.fonts.utils.ChartData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    private final SurveyTextRepository surveyTextRepository;

    private final SurveyMapper surveyMapper;

    public Long submit(SurveyDto surveyDto) {
        Survey survey = surveyMapper.toSurvey(surveyDto);
        surveyRepository.save(survey);
        return survey.getId();
    }

    public SurveyText getSurveyTextById(Long id) {
        Optional<SurveyText> surveyText = surveyTextRepository.findById(id);
        return surveyText.orElse(null);
    }

    public Long getSurveyTextCount() {
        return surveyTextRepository.count();
    }

    public List<ChartData> getRatingPerFont() {
        List<ChartData> fontsAndRatings = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts();
        for (String font: uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFont(font);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }
}
