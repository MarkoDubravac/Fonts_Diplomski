package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.*;
import com.mdubravac.fonts.entities.*;
import com.mdubravac.fonts.repositories.ParticipantSurveyRepository;
import com.mdubravac.fonts.services.SurveyResponseService;
import com.mdubravac.fonts.services.SurveyService;
import com.mdubravac.fonts.utils.ChartData;
import com.mdubravac.fonts.utils.Font;
import com.mdubravac.fonts.utils.FunFactsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final ParticipantSurveyRepository participantSurveyRepository;

    private final SurveyService surveyService;

    private final SurveyResponseService surveyResponseService;

    @GetMapping("/{uuid}")
    public ParticipantSurvey getSurvey(@PathVariable String uuid) {
        return participantSurveyRepository.findByUuid(uuid);
    }

    @GetMapping("/finished")
    public ResponseEntity<Long> getFinished(@RequestParam String session) {
        return new ResponseEntity<>(surveyService.getFinished(session), HttpStatus.OK);
    }

    @PostMapping("/responses")
    public ResponseEntity<ParticipantResponse> submitSurveyResponse(@RequestBody SurveyResponseDto surveyResponseDTO) {
        ParticipantResponse savedResponse = surveyResponseService.saveSurveyResponse(surveyResponseDTO);
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<Long> apply(@RequestBody SurveyDto surveyDto) {
        Long participantId = surveyService.submit(surveyDto);
        return ResponseEntity.ok(participantId);
    }

    @GetMapping("/review")
    public ResponseEntity<String> getReviewText(@RequestParam Long id, @RequestParam String uuid) {
        SurveyText surveyText = surveyService.getSurveyTextById(id, uuid);

        return ResponseEntity.ok(surveyText.getText());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getSurveyTextCount(@RequestParam String uuid) {

        return ResponseEntity.ok(surveyService.getSurveyTextCount(uuid));
    }

    @GetMapping("/fonts")
    public ResponseEntity<List<String>> getSurveyFonts(@RequestParam String uuid) {

        return ResponseEntity.ok(surveyService.getSurveyFonts(uuid).stream().map(Font::name).toList());
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ChartData>> getRatingStats(@RequestParam Optional<String> session, @RequestParam Optional<String> uuid) {
        return session.map(s -> ResponseEntity.ok(surveyService.getRatingPerFont(s))).orElseGet(() -> ResponseEntity.ok(surveyService.getAllRatingPerFont(uuid.get())));
    }

    @GetMapping("/stats/duration")
    public ResponseEntity<List<ChartData>> getDurationStats(@RequestParam Optional<String> session, @RequestParam Optional<String> uuid) {
        return session.map(s -> ResponseEntity.ok(surveyService.getDurationPerFont(s))).orElseGet(() -> ResponseEntity.ok(surveyService.getAllDurationsPerFont(uuid.get())));

    }

    @GetMapping("/fun-facts")
    public ResponseEntity<FunFactsData> getFunFacts(@RequestParam Optional<String> session, @RequestParam Optional<String> uuid) {
        return session.map(s -> ResponseEntity.ok(surveyService.getFunFacts(s))).orElseGet(() -> ResponseEntity.ok(surveyService.getAllFunFacts(uuid.get())));
    }
}
