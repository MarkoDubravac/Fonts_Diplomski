package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.SurveyResponseDto;
import com.mdubravac.fonts.entities.AnswerTest;
import com.mdubravac.fonts.entities.QuestionTest;
import com.mdubravac.fonts.entities.ResponseTest;
import com.mdubravac.fonts.entities.SurveyTest;
import com.mdubravac.fonts.repositories.QuestionTestRepository;
import com.mdubravac.fonts.repositories.ResponseRepository;
import com.mdubravac.fonts.repositories.SurveyTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyResponseService {
    private final SurveyTestRepository surveyRepository;

    private final ResponseRepository responseRepository;

    private final QuestionTestRepository questionRepository;

    public ResponseTest saveSurveyResponse(SurveyResponseDto surveyResponseDTO) {
        SurveyTest survey = surveyRepository.findById(surveyResponseDTO.getSurveyId())
                .orElseThrow();

        ResponseTest response = new ResponseTest();
        response.setSurvey(survey);

        List<AnswerTest> answers = surveyResponseDTO.getAnswers().stream().map(answerDTO -> {
            QuestionTest question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow();

            AnswerTest answer = new AnswerTest();
            answer.setQuestion(question);
            answer.setResponseText(answerDTO.getResponseText());
            answer.setResponse(response);

            return answer;
        }).collect(Collectors.toList());

        response.setAnswers(answers);
        response.setSurveySession(surveyResponseDTO.getSurveySession());
        return responseRepository.save(response);
    }
}
