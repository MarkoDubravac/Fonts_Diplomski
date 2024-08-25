package com.mdubravac.fonts.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminStatsDto {
    private String surveySession;
    private Double averageRating;
    private Double averageDuration;
}

