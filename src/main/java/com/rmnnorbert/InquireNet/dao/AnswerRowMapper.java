package com.rmnnorbert.InquireNet.dao;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Answer(
                rs.getInt("answer_id"),
                rs.getInt("question_id"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getInt("numberOfReply"),
                rs.getString("vote")
        );
    }

}
