package com.rmnnorbert.InquireNet.rowMapper;

import com.rmnnorbert.InquireNet.dao.model.chat.Chat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRowMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Chat(
                rs.getLong("chat_id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("role"),
                rs.getString("Content")
        );
    }
}
