package com.mdubravac.fonts.repositories;


import com.mdubravac.fonts.dto.AdminStatsDto;
import com.mdubravac.fonts.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT DISTINCT font FROM Survey WHERE surveySession = :surveySession")
    List<String> findAllWithUniqueFonts(String surveySession);

    @Query("SELECT DISTINCT font FROM Survey")
    List<String> findAllWithUniqueFonts();

    @Query("SELECT AVG(e.rating) FROM Survey e WHERE e.font = :font AND e.surveySession = :surveySession")
    Float findAverageRatingOfFontPerSession(String font, String surveySession);

    @Query("SELECT AVG(e.rating) FROM Survey e WHERE e.font = :font")
    Float findAverageRatingOfFontPerSession(String font);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font AND e.surveySession = :surveySession")
    Float findAverageDurationPerFontPerSession(String font, String surveySession);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font")
    Float findAverageDurationPerFontPerSession(String font);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MAX(s2.rating) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findHighestRatedSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MIN(s2.rating) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findLowestRatedSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MAX(s2.rating) FROM Survey s2)")
    List<String> findHighestRatedSurvey();

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MIN(s2.rating) FROM Survey s2)")
    List<String> findLowestRatedSurvey();

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MIN(s2.duration) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findLeastDurationSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MAX(s2.duration) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findHighestDurationSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MIN(s2.duration) FROM Survey s2)")
    List<String> findLeastDurationSurvey();

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MAX(s2.duration) FROM Survey s2)")
    List<String> findHighestDurationSurvey();

    @Query("SELECT new com.mdubravac.fonts.dto.AdminStatsDto(s.surveySession, AVG(s.rating), AVG(s.duration)) " +
            "FROM Survey s GROUP BY s.surveySession")
    List<AdminStatsDto> findAverageRatingAndDurationPerSession();

}
