package com.rmnnorbert.InquireNet.dao;


import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReplyRowMapper implements RowMapper<Reply> {
    @Override
    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reply(
                rs.getLong("reply_id"),
                rs.getLong("answer_id"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime()
        );
    }
}
