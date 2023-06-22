package com.rmnnorbert.InquireNet.dto.answer;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;

import java.time.LocalDateTime;

public record AnswerDTO (long answer_id,
                         long user_id,
                         long question_id,
                         String description,
                         LocalDateTime created,
                         int numberOfReply,
                         String vote)
{
    public static AnswerDTO of(Answer answer){
        return new AnswerDTO(
                answer.answer_id(),
                answer.user_id(),
                answer.question_id(),
                answer.description(),
                answer.created(),
                answer.numberOfReply(),
                answer.vote());
    }

}
