package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.AdminStatsDto;
import com.mdubravac.fonts.dto.CreateSurveyTestDto;
import com.mdubravac.fonts.dto.NewCollectionDto;
import com.mdubravac.fonts.dto.SurveyTextDto;
import com.mdubravac.fonts.entities.ParticipantQuestion;
import com.mdubravac.fonts.entities.ParticipantSurvey;
import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.entities.TextCollection;
import com.mdubravac.fonts.repositories.ParticipantSurveyRepository;
import com.mdubravac.fonts.repositories.SurveyRepository;
import com.mdubravac.fonts.repositories.TextCollectionRepository;
import com.mdubravac.fonts.services.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mdubravac.fonts.services.UuidService.generateUuid;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ParticipantSurveyRepository participantSurveyRepository;

    private final TextCollectionRepository textCollectionRepository;

    private final SurveyService surveyService;
    private final SurveyRepository surveyRepository;

    @GetMapping
    public List<ParticipantSurvey> getAllParticipantSurveys() {
        return participantSurveyRepository.findAll();
    }

    @DeleteMapping
    public void deleteParticipantSurvey(@RequestParam Long id) {
        participantSurveyRepository.deleteById(id);
    }

    @PostMapping("/createSurvey")
    public ParticipantSurvey createParticipantSurvey(@RequestBody CreateSurveyTestDto survey) {

        ParticipantSurvey participantSurvey = new ParticipantSurvey();
        participantSurvey.setUuid(generateUuid());
        participantSurvey.setTitle(survey.getTitle());

        List<ParticipantQuestion> questions = survey.getQuestions();
        for (ParticipantQuestion question : questions) {
            question.setSurvey(participantSurvey);
        }

        participantSurvey.setQuestions(questions);
        participantSurvey.setCollection(textCollectionRepository.findByName(survey.getCollectionName()));
        return participantSurveyRepository.save(participantSurvey);
    }

    @PostMapping("/createSurveyText")
    public SurveyText createSurveyText(@RequestBody SurveyTextDto surveyTextDto) {
        return surveyService.createSurveyText(surveyTextDto);
    }

    @PostMapping("/saveTextCollection")
    public ResponseEntity<Long> saveTextCollection(@RequestBody NewCollectionDto newCollectionDto) {
        TextCollection textCollection = surveyService.saveTextCollection(newCollectionDto);
        return ResponseEntity.ok(textCollection.getId());
    }

    @DeleteMapping("/deleteTextCollection")
    public void saveTextCollection(@RequestParam Long id) {
        surveyService.deleteTextCollection(id);
    }

    @GetMapping("/getTextCollections")
    public ResponseEntity<List<TextCollection>> getTextCollections() {
        List<TextCollection> textCollections = surveyService.getTextCollection();
        return ResponseEntity.ok(textCollections);
    }

    @GetMapping("/getCollectionTexts")
    public ResponseEntity<List<String>> getCollectionTexts(@RequestParam Long id) {
        List<String> textCollections = surveyService.getCollectionTexts(id);
        return ResponseEntity.ok(textCollections);
    }

    @GetMapping("/stats")
    public List<AdminStatsDto> getSurveyStats() {
        return surveyService.getAverageRatingAndDurationPerSession();
    }

    @DeleteMapping("/deleteAll")
    public void deleteSurveyResults() {
        surveyService.deleteAll();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCsv() {
        try {
            byte[] csvBytes = surveyService.exportDataToCsv();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=surveys.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exportParticipantData")
    public ResponseEntity<byte[]> exportParticipantCsv() {
        try {
            byte[] csvBytes = surveyService.exportDataParticipantToCsv();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=participant.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
