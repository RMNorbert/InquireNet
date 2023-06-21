package com.rmnnorbert.InquireNet.dto.question;

import com.rmnnorbert.InquireNet.dao.model.question.Question;

import java.time.LocalDateTime;

public record QuestionDTO(long question_id,
                          long user_id,
                          String title,
                          String description,
                          LocalDateTime created,
                          int numberOfAnswers)
{
    public static QuestionDTO of(Question question) {
        return new QuestionDTO(
                question.getQuestion_id(),
                question.getUser_id(),
                question.getTitle(),
                question.getDescription(),
                question.getCreated(),
                question.getNumberOfAnswers()
                );
    }

}
