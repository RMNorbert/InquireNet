package com.rmnnorbert.InquireNet.rowMapper.forum;

import com.rmnnorbert.InquireNet.dao.model.forum.question.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Question(
                rs.getLong("question_id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getInt("numberOfAnswers")
        );
    }
}
