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
@Table(name = "reponse_test")
public class ResponseTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveySession;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyTest survey;
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
    private List<AnswerTest> answers;
}
