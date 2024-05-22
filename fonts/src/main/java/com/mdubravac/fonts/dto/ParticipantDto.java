package com.mdubravac.fonts.dto;

import com.mdubravac.fonts.enums.ImpairmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParticipantDto {
    private Integer age;
    private Boolean hasScreenReadingIssues;
    private String otherProblems;
    private Set<ImpairmentType> impairmentTypes;
}
