package com.mdubravac.fonts.mappers;

import com.mdubravac.fonts.dto.ParticipantDto;
import com.mdubravac.fonts.entities.Participant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantDto toParticipantDto(Participant participant);

    Participant toParticipant(ParticipantDto participantDto);
}


