package com.rmnnorbert.InquireNet.dao.model.question;

import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.UpdateQuestionDTO;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestion();
    Question findQuestionById(long id);
    long findLastQuestionId();
    boolean addQuestion(NewQuestionDTO question);
    List<Question> getAllQuestionByUserID(long userID);
    boolean deleteQuestionById(long theId);
    boolean update(UpdateQuestionDTO questionDTO);

}
