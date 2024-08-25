package com.mdubravac.fonts.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "participant_survey")
public class ParticipantSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String uuid;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ParticipantQuestion> questions;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    @JsonBackReference
    private TextCollection collection;

    @Override
    public String toString() {
        return "SurveyTest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionsSize=" + (questions != null ? questions.size() : 0) +
                '}';
    }

}
