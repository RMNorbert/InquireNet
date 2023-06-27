package com.rmnnorbert.InquireNet.dao.model.answer;

import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;

import java.util.List;

public interface AnswerDAO {
    List<Answer> getAllAnswers();
    Answer findAnswerById(long id);
    List<Answer> getAllAnswersByQuestionId(long id);
    boolean addAnswer(AnswerRequestDTO answer);
    boolean deleteAnswerById(long theId);
    boolean update(String description, long id);
    void changeVote(String vote, long id);
    int getNumberOfUserAnswers(long id);
}
