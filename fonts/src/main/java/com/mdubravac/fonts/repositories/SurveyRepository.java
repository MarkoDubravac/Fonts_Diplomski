package com.mdubravac.fonts.repositories;


import com.mdubravac.fonts.dto.AdminStatsDto;
import com.mdubravac.fonts.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Long countBySurveySession(String surveySession);

    List<Survey> findAllBySurveySession(String surveySession);

    @Query("SELECT DISTINCT font FROM Survey WHERE participantSurveyUuid = :uuid")
    List<String> findAllWithUniqueFonts(String uuid);

    @Query("SELECT DISTINCT font FROM Survey WHERE surveySession = :session")
    List<String> findOneWithUniqueFonts(String session);

    @Query("SELECT AVG(e.rating) FROM Survey e WHERE e.font = :font AND e.surveySession = :surveySession")
    Float findAverageRatingOfFontPerSession(String font, String surveySession);

    @Query("SELECT AVG(e.rating) FROM Survey e WHERE e.font = :font AND e.participantSurveyUuid = :uuid")
    Float findAverageRatingOfFontPerSessionByUuid(String font, String uuid);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font AND e.surveySession = :surveySession")
    Float findAverageDurationPerFontPerSession(String font, String surveySession);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font")
    Float findAverageDurationPerFontPerSession(String font);

    @Query("SELECT AVG(e.duration) FROM Survey e WHERE e.font = :font AND e.participantSurveyUuid = :uuid")
    Float findAverageDurationPerFontPerSessionPerUuid(String font, String uuid);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MAX(s2.rating) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findHighestRatedSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MIN(s2.rating) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findLowestRatedSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MAX(s2.rating) FROM Survey s2 WHERE s2.participantSurveyUuid = :uuid) AND s.participantSurveyUuid = :uuid")
    List<String> findHighestRatedSurveyByUuid(String uuid);

    @Query("SELECT s.font FROM Survey s WHERE s.rating = (SELECT MIN(s2.rating) FROM Survey s2 WHERE s2.participantSurveyUuid = :uuid) AND s.participantSurveyUuid = :uuid")
    List<String> findLowestRatedSurveyByUuid(String uuid);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MIN(s2.duration) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findLeastDurationSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MAX(s2.duration) FROM Survey s2 WHERE s2.surveySession = :session) AND s.surveySession = :session")
    List<String> findHighestDurationSurveyBySession(String session);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MIN(s2.duration) FROM Survey s2 WHERE s2.participantSurveyUuid = :uuid) AND s.participantSurveyUuid = :uuid")
    List<String> findLeastDurationSurveyByUuid(String uuid);

    @Query("SELECT s.font FROM Survey s WHERE s.duration = (SELECT MAX(s2.duration) FROM Survey s2 WHERE s2.participantSurveyUuid = :uuid) AND s.participantSurveyUuid = :uuid")
    List<String> findHighestDurationSurveyByUuid(String uuid);

    @Query("SELECT new com.mdubravac.fonts.dto.AdminStatsDto(s.surveySession, AVG(s.rating), AVG(s.duration)) " +
            "FROM Survey s GROUP BY s.surveySession")
    List<AdminStatsDto> findAverageRatingAndDurationPerSession();

}
