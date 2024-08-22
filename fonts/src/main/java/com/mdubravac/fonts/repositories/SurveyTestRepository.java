package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.SurveyTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyTestRepository extends JpaRepository<SurveyTest, Long> {
    SurveyTest findByUuid(String uuid);
}