package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.SurveyResponseDto;
import com.mdubravac.fonts.entities.ParticipantAnswer;
import com.mdubravac.fonts.entities.ParticipantQuestion;
import com.mdubravac.fonts.entities.ParticipantResponse;
import com.mdubravac.fonts.entities.ParticipantSurvey;
import com.mdubravac.fonts.repositories.ParticipantQuestionRepository;
import com.mdubravac.fonts.repositories.ParticipantResponseRepository;
import com.mdubravac.fonts.repositories.ParticipantSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyResponseService {
    private final ParticipantSurveyRepository surveyRepository;

    private final ParticipantResponseRepository participantResponseRepository;

    private final ParticipantQuestionRepository questionRepository;

    public ParticipantResponse saveSurveyResponse(SurveyResponseDto surveyResponseDTO) {
        ParticipantSurvey survey = surveyRepository.findById(surveyResponseDTO.getSurveyId())
                .orElseThrow();

        ParticipantResponse response = new ParticipantResponse();
        response.setSurvey(survey);

        List<ParticipantAnswer> answers = surveyResponseDTO.getAnswers().stream().map(answerDTO -> {
            ParticipantQuestion question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow();

            ParticipantAnswer answer = new ParticipantAnswer();
            answer.setQuestion(question);
            answer.setResponseText(answerDTO.getResponseText());
            answer.setResponse(response);

            return answer;
        }).collect(Collectors.toList());

        response.setAnswers(answers);
        response.setSurveySession(surveyResponseDTO.getSurveySession());
        return participantResponseRepository.save(response);
    }
}
