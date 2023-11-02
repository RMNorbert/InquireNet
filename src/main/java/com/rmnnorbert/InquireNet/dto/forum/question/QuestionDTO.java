package com.rmnnorbert.InquireNet.dto.forum.question;

import com.rmnnorbert.InquireNet.dao.model.forum.question.Question;

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
                question.question_id(),
                question.user_id(),
                question.title(),
                question.description(),
                question.created(),
                question.numberOfAnswers()
                );
    }

}
