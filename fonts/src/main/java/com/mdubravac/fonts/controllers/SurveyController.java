package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.*;
import com.mdubravac.fonts.entities.*;
import com.mdubravac.fonts.repositories.SurveyTestRepository;
import com.mdubravac.fonts.repositories.TextCollectionRepository;
import com.mdubravac.fonts.services.SurveyResponseService;
import com.mdubravac.fonts.services.SurveyService;
import com.mdubravac.fonts.utils.ChartData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mdubravac.fonts.services.UuidService.generateUuid;


@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyTestRepository surveyTestRepository;

    private final SurveyService surveyService;

    private final SurveyResponseService surveyResponseService;

    private final TextCollectionRepository textCollectionRepository;

    @GetMapping
    public List<SurveyTest> getAllSurveys() {
        return surveyTestRepository.findAll();
    }

    @DeleteMapping
    public void getAllSurveys(@RequestParam Long id) {
        surveyTestRepository.deleteById(id);
    }

    @PostMapping("/createSurvey")
    public SurveyTest createSurvey(@RequestBody CreateSurveyTestDto survey) {

        SurveyTest surveyTest = new SurveyTest();
        surveyTest.setUuid(generateUuid());
        surveyTest.setTitle(survey.getTitle());

        List<QuestionTest> questions = survey.getQuestions();
        for (QuestionTest question : questions) {
            question.setSurvey(surveyTest);
        }

        surveyTest.setQuestions(questions);
        surveyTest.setCollection(textCollectionRepository.findByName(survey.getCollectionName()));
        return surveyTestRepository.save(surveyTest);
    }

    @PostMapping("/createSurveyText")
    public SurveyText createSurveyText(@RequestBody SurveyTextDto surveyTextDto) {
        return surveyService.createSurveyText(surveyTextDto);
    }

    @GetMapping("/{uuid}")
    public SurveyTest getSurvey(@PathVariable String uuid) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(surveyTestRepository.findByUuid(uuid));
        return surveyTestRepository.findByUuid(uuid);
    }

    @PostMapping("/responses")
    public ResponseEntity<ResponseTest> submitSurveyResponse(@RequestBody SurveyResponseDto surveyResponseDTO) {
        ResponseTest savedResponse = surveyResponseService.saveSurveyResponse(surveyResponseDTO);
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<Long> apply(@RequestBody SurveyDto surveyDto) {
        Long participantId = surveyService.submit(surveyDto);
        return ResponseEntity.ok(participantId);
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

    @GetMapping("/review")
    public ResponseEntity<String> getReviewText(@RequestParam Long id) {
        SurveyText surveyText = surveyService.getSurveyTextById(id);
        return ResponseEntity.ok(surveyText.getText());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getSurveyTextCount(@RequestParam String uuid) {

        return ResponseEntity.ok(surveyService.getSurveyTextCount(uuid));
    }

    @GetMapping("/fonts")
    public ResponseEntity<List<String>> getSurveyFonts(@RequestParam String uuid) {

        return ResponseEntity.ok(surveyService.getSurveyFonts(uuid));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ChartData>> getRatingStats() {
        return ResponseEntity.ok(surveyService.getRatingPerFont());
    }

    @GetMapping("/stats/duration")
    public ResponseEntity<List<ChartData>> getDurationStats() {
        return ResponseEntity.ok(surveyService.getDurationPerFont());
    }
}
