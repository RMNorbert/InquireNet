package com.rmnnorbert.InquireNet.dao.model.forum.answer;

import com.rmnnorbert.InquireNet.dto.forum.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;

import java.util.List;

public interface AnswerDAO {
    List<Answer> getAllAnswers();
    Answer findAnswerById(long id);
    List<Answer> getAllAnswersByQuestionId(long id);
    boolean addAnswer(AnswerRequestDTO answer);
    boolean deleteAnswerById(long theId);
    boolean update(UpdateDTO updateDTO);
    boolean changeVote(String vote, long id);
    int getNumberOfUserAnswers(long id);
    List<Answer> getAllAnswersByUserId(long id);
}
