package com.mdubravac.fonts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "participant_reponse")
public class ParticipantResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveySession;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private ParticipantSurvey survey;
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
    private List<ParticipantAnswer> answers;
}
