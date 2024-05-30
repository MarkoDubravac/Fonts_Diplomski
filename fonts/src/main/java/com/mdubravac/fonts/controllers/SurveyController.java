package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.entities.SurveyText;
import com.mdubravac.fonts.services.SurveyService;
import com.mdubravac.fonts.utils.ChartData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/submit")
    public ResponseEntity<Long> apply(@RequestBody SurveyDto surveyDto) {
        Long participantId = surveyService.submit(surveyDto);
        return ResponseEntity.ok(participantId);
    }

    @GetMapping("/review")
    public ResponseEntity<String> getReviewText(@RequestParam Long id) {
        SurveyText surveyText = surveyService.getSurveyTextById(id);
        return ResponseEntity.ok(surveyText.getText());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getSurveyTextCount() {
        return ResponseEntity.ok(surveyService.getSurveyTextCount());
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ChartData>> getStats() {
        return ResponseEntity.ok(surveyService.getRatingPerFont());
    }
}
