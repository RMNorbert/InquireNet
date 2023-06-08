package com.rmnnorbert.InquireNet.dao.model.question;

import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionDAO {
    List<Question> getAllQuestion();
    Optional<Question> findQuestionById(int id);
    Optional<Question> findLastQuestion();
    int addQuestion(NewQuestionDTO question);
    List<Question> getAllQuestionByUserID(int userID);
    boolean deleteQuestionById(int theId);
    void update(QuestionDTO questionDTO, int id);

}
