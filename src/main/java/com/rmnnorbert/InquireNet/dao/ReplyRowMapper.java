package com.rmnnorbert.InquireNet.dao;


import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReplyRowMapper implements RowMapper<Reply> {
    @Override
    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reply(
                rs.getInt("reply_id"),
                rs.getInt("answer_id"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime()
        );
    }
}
