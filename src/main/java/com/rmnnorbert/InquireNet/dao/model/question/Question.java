package com.rmnnorbert.InquireNet.dao.model.question;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Question {
    private long question_id;
    private long user_id;
    private String title;
    private String description;
    private LocalDateTime created;
    private int numberOfAnswers;

    public Question(long question_id, long user_id, String title, String description, LocalDateTime created, int numberOfAnswers) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.created = created;
        this.numberOfAnswers = numberOfAnswers;
    }

    public Question() {}


}
