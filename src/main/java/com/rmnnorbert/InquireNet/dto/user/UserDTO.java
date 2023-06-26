package com.rmnnorbert.InquireNet.dto.user;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public record UserDTO(long id,
                      Role role,
                      String username,
                      LocalDateTime registration_date
                      )
{
    public static UserDTO of(User user) {
        return new UserDTO(user.getId(),
                user.getRole(), user.getUsername(),
                user.getRegistration_date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && role == userDTO.role && username.equals(userDTO.username) && registration_date.equals(userDTO.registration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, username, registration_date);
    }
}
