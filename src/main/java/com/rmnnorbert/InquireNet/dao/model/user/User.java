package com.rmnnorbert.InquireNet.dao.model.user;

import java.time.LocalDateTime;

public class User {

    public User(int id, String status, String name, String password, LocalDateTime registration_date, Integer number_of_questions, Integer number_of_answers) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.password = password;
        this.registration_date = registration_date;
        this.number_of_questions = number_of_questions;
        this.number_of_answers = number_of_answers;
    }
    private int id;
    private String status;
    private String name;
    private String password;
    private LocalDateTime registration_date;
    private Integer number_of_questions;
    private Integer number_of_answers;

    public User() {}

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDateTime registration_date) {
        this.registration_date = registration_date;
    }

    public Integer getNumber_of_questions() {
        return number_of_questions;
    }

    public void setNumber_of_questions(Integer number_of_questions) {
        this.number_of_questions = number_of_questions;
    }

    public Integer getNumber_of_answers() {
        return number_of_answers;
    }

    public void setNumber_of_answers(Integer number_of_answers) {
        this.number_of_answers = number_of_answers;
    }
}
