package com.mdubravac.fonts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SurveyTextDto {
    private String text;
    private String textCollectionName;
}
