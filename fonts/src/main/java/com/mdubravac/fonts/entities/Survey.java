package com.mdubravac.fonts.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "font", nullable = false)
    private String font;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @Column(name = "survey_session", nullable = false)
    private String surveySession;
    @Column(name = "participant_survey_uuid", nullable = false)
    private String participantSurveyUuid;

}
