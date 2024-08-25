package com.mdubravac.fonts.dto;

import com.mdubravac.fonts.entities.ParticipantQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateSurveyTestDto {
    private String title;
    private String uuid;
    private List<ParticipantQuestion> questions;
    private String collectionName;
}
