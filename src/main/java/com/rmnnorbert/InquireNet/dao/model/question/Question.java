package com.rmnnorbert.InquireNet.dao.model.question;

import java.time.LocalDateTime;

public class Question {
    private Integer question_id;
    private int user_id;
    private String title;
    private String description;
    private LocalDateTime created;
    private int numberOfAnswers;

    public Question(Integer question_id, int user_id, String title, String description, LocalDateTime created, int numberOfAnswers) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.created = created;
        this.numberOfAnswers = numberOfAnswers;
    }

    public Question() {}


    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }
}
