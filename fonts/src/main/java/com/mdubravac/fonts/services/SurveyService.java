package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.NewCollectionDto;
import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.dto.SurveyTextDto;
import com.mdubravac.fonts.entities.Survey;
import com.mdubravac.fonts.entities.SurveyTest;
import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.entities.TextCollection;
import com.mdubravac.fonts.mappers.SurveyMapper;
import com.mdubravac.fonts.repositories.SurveyRepository;
import com.mdubravac.fonts.repositories.SurveyTestRepository;
import com.mdubravac.fonts.repositories.SurveyTextRepository;
import com.mdubravac.fonts.repositories.TextCollectionRepository;
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

    private final TextCollectionRepository textCollectionRepository;
    private final SurveyTestRepository surveyTestRepository;

    public Long submit(SurveyDto surveyDto) {
        Survey survey = surveyMapper.toSurvey(surveyDto);
        surveyRepository.save(survey);
        return survey.getId();
    }

    public SurveyText createSurveyText(SurveyTextDto surveyTextDto) {
        System.out.println(surveyTextDto);
        SurveyText surveyText = new SurveyText();
        TextCollection textCollection = textCollectionRepository.findByName(surveyTextDto.getTextCollectionName());
        surveyText.setText(surveyTextDto.getText());
        surveyText.setCollection(textCollection);
        return surveyTextRepository.save(surveyText);
    }

    public SurveyText getSurveyTextById(Long id) {
        Optional<SurveyText> surveyText = surveyTextRepository.findById(id);
        return surveyText.orElse(null);
    }

    public Long getSurveyTextCount(String uuid) {
        SurveyTest surveyTest = surveyTestRepository.findByUuid(uuid);
        List<SurveyText> texts = surveyTest.getCollection().getSurveyTexts();
        return (long) texts.size();
    }

    public List<String> getSurveyFonts(String uuid) {
        SurveyTest surveyTest = surveyTestRepository.findByUuid(uuid);
        return surveyTest.getCollection().getFonts();
    }

    public List<ChartData> getRatingPerFont() {
        List<ChartData> fontsAndRatings = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts();
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFont(font);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }

    public List<ChartData> getDurationPerFont() {
        List<ChartData> fontsAndTimes = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts();
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageDurationPerFont(font);
            ChartData data = new ChartData(font, rating);
            fontsAndTimes.add(data);
        }
        return fontsAndTimes;
    }

    public TextCollection saveTextCollection(NewCollectionDto newCollectionDto) {
        TextCollection textCollection = new TextCollection();
        textCollection.setName(newCollectionDto.getName());
        textCollection.setFonts(newCollectionDto.getFonts());
        return textCollectionRepository.save(textCollection);
    }

    public void deleteTextCollection(Long id) {
        textCollectionRepository.deleteById(id);
    }

    public List<TextCollection> getTextCollection() {
        return textCollectionRepository.findAll();
    }
}
