package com.mdubravac.fonts.repositories;


import com.mdubravac.fonts.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
