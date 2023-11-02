package com.rmnnorbert.InquireNet.dao.model.forum.question;

import com.rmnnorbert.InquireNet.dto.forum.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;

import java.util.List;

public interface QuestionDAO {
    List<Question> getAllQuestion();
    Question findQuestionById(long id);
    long findLastQuestionId();
    boolean addQuestion(NewQuestionDTO question);
    List<Question> getAllQuestionByUserID(long userID);
    boolean deleteQuestionById(long theId);
    boolean update(UpdateDTO updateDTO);

}
