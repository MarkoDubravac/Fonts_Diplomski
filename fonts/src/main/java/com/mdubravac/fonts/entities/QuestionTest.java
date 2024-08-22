package com.mdubravac.fonts.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mdubravac.fonts.enums.QuestionType;
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
@Table(name = "question_test")
public class QuestionTest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean optional;
    private QuestionType type;
    @ElementCollection
    private List<String> options;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonBackReference
    private SurveyTest survey;

    @Override
    public String toString() {
        return "QuestionTest{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", options=" + options +
                '}';
    }

}