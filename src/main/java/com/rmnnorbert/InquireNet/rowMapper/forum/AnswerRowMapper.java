package com.rmnnorbert.InquireNet.rowMapper.forum;

import com.rmnnorbert.InquireNet.dao.model.forum.answer.Answer;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Answer(
                rs.getLong("answer_id"),
                rs.getLong("user_id"),
                rs.getLong("question_id"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getInt("numberOfReply"),
                rs.getString("vote")
        );
    }

}
