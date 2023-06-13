package com.rmnnorbert.InquireNet.dao.model.answer;

import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerDAO {
    List<Answer> getAllAnswers();

    Optional<Answer> findAnswerById(int id);
    List<Answer> getAllAnswersByQuestionId(int id);

    int addAnswer(NewAnswerDTO answer);

    boolean deleteAnswerById(int theId);

    void update(AnswerDTO answerDTO, int id);
}