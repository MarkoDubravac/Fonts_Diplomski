package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.dto.ParticipantDto;
import com.mdubravac.fonts.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/apply")
    public ResponseEntity<Long> apply(@RequestBody ParticipantDto participantDto) {
        System.out.println("AAAAAAAAAAAAAAA");
        Long participantId = participantService.apply(participantDto);
        return ResponseEntity.ok(participantId);
    }
}
