package com.rmnnorbert.InquireNet.dto.answer;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;

import java.time.LocalDateTime;

public record AnswerDTO (long answer_id,
                         long question_id,
                         String description,
                         LocalDateTime created,
                         int numberOfReply,
                         String vote)
{
    public static AnswerDTO of(Answer answer){
        return new AnswerDTO(
                answer.getAnswer_id(),
                answer.getQuestion_id(),
                answer.getDescription(),
                answer.getCreated(),
                answer.getNumberOfReply(),
                answer.getVote());
    }

}
