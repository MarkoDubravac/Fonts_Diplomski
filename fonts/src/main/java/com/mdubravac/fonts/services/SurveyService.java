package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.AdminStatsDto;
import com.mdubravac.fonts.dto.NewCollectionDto;
import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.dto.SurveyTextDto;
import com.mdubravac.fonts.entities.*;
import com.mdubravac.fonts.mappers.SurveyMapper;
import com.mdubravac.fonts.repositories.*;
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

    private final ParticipantResponseRepository participantResponseRepository;


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
        List<String> uniqueFonts = surveyRepository.findOneWithUniqueFonts(session);
        System.out.println(uniqueFonts);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFontPerSession(font, session);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }

    public List<ChartData> getAllRatingPerFont(String uuid) {
        List<ChartData> fontsAndRatings = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts(uuid);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageRatingOfFontPerSessionByUuid(font, uuid);
            ChartData data = new ChartData(font, rating);
            fontsAndRatings.add(data);
        }
        return fontsAndRatings;
    }


    public List<ChartData> getDurationPerFont(String session) {
        List<ChartData> fontsAndTimes = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findOneWithUniqueFonts(session);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageDurationPerFontPerSession(font, session);
            ChartData data = new ChartData(font, rating);
            fontsAndTimes.add(data);
        }
        return fontsAndTimes;
    }

    public List<ChartData> getAllDurationsPerFont(String uuid) {
        List<ChartData> fontsAndTimes = new ArrayList<>();
        List<String> uniqueFonts = surveyRepository.findAllWithUniqueFonts(uuid);
        for (String font : uniqueFonts) {
            Float rating = surveyRepository.findAverageDurationPerFontPerSessionPerUuid(font, uuid);
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

    public FunFactsData getAllFunFacts(String uuid) {
        List<String> highestRated = surveyRepository.findHighestRatedSurveyByUuid(uuid);
        List<String> lowestDuration = surveyRepository.findLeastDurationSurveyByUuid(uuid);
        List<String> lowestRated = surveyRepository.findLowestRatedSurveyByUuid(uuid);
        List<String> highestDuration = surveyRepository.findHighestDurationSurveyByUuid(uuid);
        return new FunFactsData(highestRated, lowestDuration, lowestRated, highestDuration);
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

    public void deleteAll() {
        surveyRepository.deleteAll();
    }

    public byte[] exportDataToCsv() {
        try {
            StringBuilder csvContent = new StringBuilder();
            csvContent.append("Survey ID, Font, Duration, Participant Session, Participant Survey UUID\n");
            List<Survey> surveys = surveyRepository.findAll();
            for (Survey survey : surveys) {
                csvContent.append(survey.getId())
                        .append(",")
                        .append(survey.getFont())
                        .append(",")
                        .append(survey.getDuration())
                        .append(",")
                        .append(survey.getSurveySession())
                        .append(",")
                        .append(survey.getParticipantSurveyUuid())
                        .append("\n");
            }
            return csvContent.toString().getBytes();

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV", e);
        }
    }

    public byte[] exportDataParticipantToCsv() {
        try {
            StringBuilder csvContent = new StringBuilder();

            csvContent.append("Participant Survey ID, Participant Survey UUID, Participant Session, Session Response ID, Question, Answer Text\n");

            List<ParticipantSurvey> participantSurveys = participantSurveyRepository.findAll();

            for (ParticipantSurvey survey : participantSurveys) {
                List<ParticipantResponse> responses = participantResponseRepository.findBySurvey(survey);

                for (ParticipantResponse response : responses) {
                    List<ParticipantAnswer> answers = response.getAnswers();

                    for (ParticipantAnswer answer : answers) {
                        csvContent.append(survey.getId())
                                .append(",")
                                .append(survey.getUuid())
                                .append(",")
                                .append(response.getSurveySession())
                                .append(",")
                                .append(response.getId())
                                .append(",")
                                .append(answer.getQuestion().getText())
                                .append(",")
                                .append(answer.getResponseText())
                                .append("\n");
                    }
                }
            }

            return csvContent.toString().getBytes();

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV", e);
        }
    }


    public Long getFinished(String session) {
        return surveyRepository.countBySurveySession(session);
    }
}
