package com.mdubravac.fonts.repositories;


import com.mdubravac.fonts.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT DISTINCT font FROM Survey")
    List<String> findAllWithUniqueFonts();
    @Query("SELECT AVG(e.rating) FROM Survey e WHERE e.font = :font")
    Float findAverageRatingOfFont(String font);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font")
    Float findAverageDurationPerFont(String font);
}
