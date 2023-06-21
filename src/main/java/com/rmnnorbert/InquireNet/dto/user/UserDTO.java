package com.rmnnorbert.InquireNet.dto.user;

import com.rmnnorbert.InquireNet.dao.model.user.User;

import java.time.LocalDateTime;

public record UserDTO(long id,
                      String status,
                      String name,
                      LocalDateTime registration_date,
                      int number_of_questions,
                      int number_of_answers)
{
    public static UserDTO of(User user) {
        return new UserDTO(user.getId(),
                user.getStatus(), user.getName(),
                user.getRegistration_date(),
                user.getNumber_of_questions(),
                user.getNumber_of_answers());
    }
}
