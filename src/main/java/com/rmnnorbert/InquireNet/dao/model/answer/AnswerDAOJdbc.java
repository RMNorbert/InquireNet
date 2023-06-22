package com.rmnnorbert.InquireNet.dao.model.answer;

import com.rmnnorbert.InquireNet.dao.AnswerRowMapper;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AnswerDAOJdbc implements AnswerDAO{
    private final JdbcTemplate jdbcTemplate;
    private final static String DEFAULT_VOTE = "unvoted";
    @Autowired
    public AnswerDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Answer> getAllAnswers() {
        String sql = "SELECT answer.answer_id, answer.user_id, question_id, answer.description, answer.created, vote," +
                " COUNT(reply_id) AS numberOfReply" +
                " FROM answer" +
                " LEFT JOIN reply r ON answer.answer_id = r.answer_id"+
                " GROUP BY answer.answer_id";
        return jdbcTemplate.query(sql, new AnswerRowMapper());
    }

    @Override
    public Answer findAnswerById(long id) {
        String sql = "SELECT answer.answer_id, answer.user_id, answer.question_id, answer.description, answer.created," +
                " answer.vote, COUNT(reply_id) AS numberOfReply" +
                " FROM answer" +
                " LEFT JOIN reply r ON answer.answer_id = r.answer_id" +
                " WHERE answer.answer_id = ?" +
                " GROUP BY answer.answer_id";

        return jdbcTemplate.query(sql, new AnswerRowMapper(),id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Answer"));
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(long id) {
        String sql = "SELECT answer.answer_id, answer.user_id, answer.question_id, answer.description, answer.created," +
                " answer.vote, COUNT(reply_id) AS numberOfReply" +
                " FROM answer" +
                " LEFT JOIN reply r ON answer.answer_id = r.answer_id" +
                " WHERE answer.question_id = ?" +
                " GROUP BY answer.answer_id";
        return jdbcTemplate.query(sql, new AnswerRowMapper(),id);
    }


    @Override
    public int addAnswer(AnswerRequestDTO answerRequestDTO) {
        String sql = "INSERT INTO answer(user_id ,description,created, question_id, vote) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                                   answerRequestDTO.userId(),
                                   answerRequestDTO.description(),
                                   LocalDateTime.now(),
                                   answerRequestDTO.id(),
                                   DEFAULT_VOTE);
    }

    @Override
    public boolean deleteAnswerById(long theId) {
        String sql = "DELETE FROM answer WHERE answer_id = ?";
        return jdbcTemplate.update(sql, theId) == 1;
    }

    @Override
    public boolean update(String description, long id) {
        String sql = "UPDATE answer SET description = ? WHERE answer_id = ?";
        return jdbcTemplate.update(sql, description, id) == 1;
    }

    @Override
    public void changeVote(String vote, long id) {
        String sql = "UPDATE answer set vote = ? WHERE answer_id = ?";
        jdbcTemplate.update(sql, vote, id);
    }
}
