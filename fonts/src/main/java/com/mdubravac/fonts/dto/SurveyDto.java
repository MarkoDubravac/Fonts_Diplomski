package com.mdubravac.fonts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {
    private Long id;
    private String font;
    private Integer duration;
    private Integer rating;
}
