package com.mdubravac.fonts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "answer_test")
public class AnswerTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String responseText;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionTest question;
    @ManyToOne
    @JoinColumn(name = "response_id")
    private ResponseTest response;
}
