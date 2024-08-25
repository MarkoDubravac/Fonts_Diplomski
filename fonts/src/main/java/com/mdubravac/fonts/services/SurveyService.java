package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.AdminStatsDto;
import com.mdubravac.fonts.dto.NewCollectionDto;
import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.dto.SurveyTextDto;
import com.mdubravac.fonts.entities.Survey;
import com.mdubravac.fonts.entities.ParticipantSurvey;
import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.entities.TextCollection;
import com.mdubravac.fonts.mappers.SurveyMapper;
import com.mdubravac.fonts.repositories.SurveyRepository;
import com.mdubravac.fonts.repositories.ParticipantSurveyRepository;
import com.mdubravac.fonts.repositories.SurveyTextRepository;
import com.mdubravac.fonts.repositories.TextCollectionRepository;
import com.mdubravac.fonts.utils.ChartData;
import com.mdubravac.fonts.utils.Font;
import com.mdubravac.fonts.utils.FunFactsData;
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
    private final ParticipantSurveyRepository participantSurveyRepository;

    public Long submit(SurveyDto surveyDto) {
        Survey survey = surveyMapper.toSurvey(surveyDto);
        surveyRepository.save(survey);
        return survey.getId();
    }

    public SurveyText createSurveyText(SurveyTextDto surveyTextDto) {
        System.out.println(surveyTextDto);
        SurveyText surveyText = new SurveyText();
        TextCollection textCollection = textCollectionRepository.findByName(surveyTextDto.getTextCollectionName());
        Long localId = surveyTextRepository.countByCollectionId(textCollection.getId()) + 1;
        surveyText.setText(surveyTextDto.getText());
        surveyText.setCollection(textCollection);
        surveyText.setLocalId(localId);
        return surveyTextRepository.save(surveyText);
    }

    public SurveyText getSurveyTextById(Long id, String uuid) {
        ParticipantSurvey participantSurvey = participantSurveyRepository.findByUuid(uuid);
        Long collectionId = participantSurvey.getCollection().getId();
        Optional<SurveyText> surveyText = surveyTextRepository.findByLocalIdAndCollectionId(id, collectionId);
        return surveyText.orElse(null);
    }

    public Long getSurveyTextCount(String uuid) {
        ParticipantSurvey participantSurvey = participantSurveyRepository.findByUuid(uuid);
        List<SurveyText> texts = participantSurvey.getCollection().getSurveyTexts();
        return (long) texts.size();
    }

    public List<Font> getSurveyFonts(String uuid) {
        ParticipantSurvey participantSurvey = participantSurveyRepository.findByUuid(uuid);
        return participantSurvey.getCollection().getFonts();
    }

    public List<ChartData> getRatingPerFont(String session) {
        List<ChartData> fontsAndRatings = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts(session);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFontPerSession(font, session);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }

    public List<ChartData> getRatingPerFont() {
        List<ChartData> fontsAndRatings = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts();
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFontPerSession(font);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }


    public List<ChartData> getDurationPerFont(String session) {
        List<ChartData> fontsAndTimes = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts(session);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageDurationPerFontPerSession(font, session);
            ChartData data = new ChartData(font, rating);
            fontsAndTimes.add(data);
        }
        return fontsAndTimes;
    }

    public List<ChartData> getDurationPerFont() {
        List<ChartData> fontsAndTimes = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts();
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageDurationPerFontPerSession(font);
            ChartData data = new ChartData(font, rating);
            fontsAndTimes.add(data);
        }
        return fontsAndTimes;
    }

    public FunFactsData getFunFacts(String session) {
        List<String> highestRated = surveyRepository.findHighestRatedSurveyBySession(session);
        List<String> lowestDuration = surveyRepository.findLeastDurationSurveyBySession(session);
        List<String> lowestRated = surveyRepository.findLowestRatedSurveyBySession(session);
        List<String> highestDuration = surveyRepository.findHighestDurationSurveyBySession(session);
        return new FunFactsData(highestRated, lowestDuration, lowestRated, highestDuration);
    }

    public FunFactsData getFunFacts() {
        List<String> highestRated = surveyRepository.findHighestRatedSurvey();
        List<String> lowestDuration = surveyRepository.findLeastDurationSurvey();
        List<String> lowestRated = surveyRepository.findLowestRatedSurvey();
        List<String> highestDuration = surveyRepository.findHighestDurationSurvey();
        return new FunFactsData(highestRated, lowestDuration, lowestRated, highestDuration);
    }

    public TextCollection saveTextCollection(NewCollectionDto newCollectionDto) {
        TextCollection textCollection = new TextCollection();
        System.out.println(newCollectionDto.getFonts());
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

    public List<String> getCollectionTexts(Long id) {
        List<String> texts = new ArrayList<>();
        Optional<TextCollection> textCollection = textCollectionRepository.findById(id);
        if (textCollection.isPresent()) {
            List<SurveyText> surveyTexts = textCollection.get().getSurveyTexts();
            return surveyTexts.stream().map(SurveyText::getText).toList();
        }
        return texts;
    }

    public List<AdminStatsDto> getAverageRatingAndDurationPerSession() {
        return surveyRepository.findAverageRatingAndDurationPerSession();

    }
}
