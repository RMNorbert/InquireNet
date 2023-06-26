package com.rmnnorbert.InquireNet.dao;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String roleValue = rs.getString("role");
        Role role = Role.valueOf(roleValue);
        return new User(
                rs.getLong("id"),
                role,
                rs.getString("username"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime()
        );
    }
}
