package com.mdubravac.fonts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SurveyResponseDto {
    private Long surveyId;
    private String surveySession;
    private List<AnswerDto> answers;
}