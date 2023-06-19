package com.rmnnorbert.InquireNet.dao.model.answer;

import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerDAO {
    List<Answer> getAllAnswers();

    Optional<Answer> findAnswerById(int id);
    List<Answer> getAllAnswersByQuestionId(int id);

    int addAnswer(NewAnswerDTO answer);

    boolean deleteAnswerById(int theId);
    boolean deleteAnswerByQuestionId(int theId);

    void update(String vote, int id);
    void changeVote(String vote, int id);
}
