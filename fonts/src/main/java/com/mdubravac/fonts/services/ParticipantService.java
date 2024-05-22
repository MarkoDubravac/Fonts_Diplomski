package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.ParticipantDto;
import com.mdubravac.fonts.entities.Impairment;
import com.mdubravac.fonts.entities.Participant;
import com.mdubravac.fonts.exceptions.ApplicationException;
import com.mdubravac.fonts.mappers.ParticipantMapper;
import com.mdubravac.fonts.repositories.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public Long apply(ParticipantDto participantDto) {
        if (participantDto.getAge() == null) {
            throw new ApplicationException("Age cannot be null", HttpStatus.BAD_REQUEST);
        }
        Participant participant = participantMapper.toParticipant(participantDto);
        Set<Impairment> impairments = participantDto.getImpairmentTypes().stream().map(Impairment::new).collect(Collectors.toSet());
        System.out.println(participantDto);
        System.out.println(participant);
        participant.setImpairments(impairments);
        participantRepository.save(participant);
        return participant.getId();
    }


}
