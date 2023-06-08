package com.rmnnorbert.InquireNet.dao;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("status"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getInt("number_of_questions"),
                rs.getInt("number_of_answers")
        );
    }
}
