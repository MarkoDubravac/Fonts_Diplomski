package com.mdubravac.fonts.entities;

import com.mdubravac.fonts.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "has_screen_reading_issues")
    private Boolean hasScreenReadingIssues = false;
    @Column(name = "unlisted_impairment")
    private String otherProblems;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participant_impairments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "impairment_id"))
    private Set<Impairment> impairments = new HashSet<>();
}

