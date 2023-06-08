package com.rmnnorbert.InquireNet.dto.question;

import java.time.LocalDateTime;

public class QuestionDTO {
    private final Integer question_id;
    private final int user_id;
    private String title;
    private String description;
    private LocalDateTime created;
    private int numberOfAnswers;

    private QuestionDTO(Integer question_id,
                        int user_id,
                        String title,
                        String description,
                        LocalDateTime created,
                        int numberOfAnswers) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.created = created;
        this.numberOfAnswers = numberOfAnswers;
    }

    public static QuestionDTO of(Question question) {
        return new QuestionDTO(
                question.getQuestion_id(),
                question.getUserID(),
                question.getTitle(),
                question.getDescription(),
                question.getCreated(),
                question.getNumberOfAnswers()
        );
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }
}
