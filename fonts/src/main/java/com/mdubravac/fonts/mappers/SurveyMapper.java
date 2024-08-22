package com.mdubravac.fonts.mappers;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.entities.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
    SurveyDto toSurveyDto(Survey survey);

    @Mapping(target = "surveySession", source = "surveySession")
    Survey toSurvey(SurveyDto surveyDto);
}
