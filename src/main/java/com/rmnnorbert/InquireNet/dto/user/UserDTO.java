package com.rmnnorbert.InquireNet.dto.user;

import com.rmnnorbert.InquireNet.dao.model.user.User;

import java.time.LocalDateTime;

public class UserDTO {
    private final int id;
    private final String status;
    private final String name;
    private final LocalDateTime registration_date;
    private int number_of_questions;
    private int number_of_answers;

    private UserDTO(int id, String status, String name, LocalDateTime registration_date, int number_of_questions, int number_of_answers) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.registration_date = registration_date;
        this.number_of_questions = number_of_questions;
        this.number_of_answers = number_of_answers;
    }

    public static UserDTO of(User user) {
        return new UserDTO(user.getId(),
                user.getStatus(), user.getName(),
                user.getRegistration_date(),
                user.getNumber_of_questions(),
                user.getNumber_of_answers());
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegistration_date() {
        return registration_date;
    }

    public int getNumber_of_questions() {
        return number_of_questions;
    }

    public int getNumber_of_answers() {
        return number_of_answers;
    }
}
