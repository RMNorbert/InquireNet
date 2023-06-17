package com.rmnnorbert.InquireNet.dto.answer;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;

import java.time.LocalDateTime;

public class AnswerDTO {
    private final int answer_id;
    private final int question_id;
    private final String description;
    private final LocalDateTime created;
    private final int numberOfReply;
    private final String vote;

    private AnswerDTO(int answer_id,
                      int question_id,
                      String description,
                      LocalDateTime created,
                      int numberOfReply,
                      String vote) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.description = description;
        this.created = created;
        this.numberOfReply = numberOfReply;
        this.vote = vote;
    }
    public static AnswerDTO of(Answer answer){
        return new AnswerDTO(
                answer.getAnswer_id(),
                answer.getQuestion_id(),
                answer.getDescription(),
                answer.getCreated(),
                answer.getNumberOfReply(),
                answer.getVote());
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getNumberOfReply() {
        return numberOfReply;
    }

    public String getVote() {return vote;}
}
