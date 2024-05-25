package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.services.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/submit")
    public ResponseEntity<Long> apply(@RequestBody SurveyDto surveyDto) {
        Long participantId = surveyService.submit(surveyDto);
        return ResponseEntity.ok(participantId);
    }
}
