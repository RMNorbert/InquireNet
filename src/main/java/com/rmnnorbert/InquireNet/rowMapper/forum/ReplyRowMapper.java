package com.rmnnorbert.InquireNet.rowMapper.forum;


import com.rmnnorbert.InquireNet.dao.model.forum.reply.Reply;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReplyRowMapper implements RowMapper<Reply> {
    @Override
    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reply(
                rs.getLong("reply_id"),
                rs.getLong("user_id"),
                rs.getLong("answer_id"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime()
        );
    }
}
