package com.rmnnorbert.InquireNet.dao.model.answer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Answer {
    private long answer_id;
    private long question_id;
    private String description;
    private LocalDateTime created;
    private int numberOfReply;
    private String vote;
    public Answer() {}

    public Answer(long answer_id, long question_id, String description, LocalDateTime created, int numberOfReply, String vote) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.description = description;
        this.created = created;
        this.numberOfReply = numberOfReply;
        this.vote = vote;
    }

}
