package com.rmnnorbert.InquireNet.dao.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class User {

    public User(long id, String status, String name, String password, LocalDateTime registration_date, int number_of_questions, int number_of_answers) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.password = password;
        this.registration_date = registration_date;
        this.number_of_questions = number_of_questions;
        this.number_of_answers = number_of_answers;
    }
    private long id;
    private String status;
    private String name;
    private String password;
    private LocalDateTime registration_date;
    private int number_of_questions;
    private int number_of_answers;

    public User() {}

}
