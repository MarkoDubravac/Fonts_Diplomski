package com.mdubravac.fonts.mappers;

import com.mdubravac.fonts.dto.SurveyDto;
import com.mdubravac.fonts.entities.Survey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
    SurveyDto toSurveyDto(Survey survey);

    Survey toSurvey(SurveyDto surveyDto);
}
