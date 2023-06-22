package com.rmnnorbert.InquireNet.dto.user;

import com.rmnnorbert.InquireNet.dao.model.user.User;

import java.time.LocalDateTime;

public record UserDTO(long id,
                      String status,
                      String name,
                      LocalDateTime registration_date
                      )
{
    public static UserDTO of(User user) {
        return new UserDTO(user.getId(),
                user.getStatus(), user.getName(),
                user.getRegistration_date());
    }
}
