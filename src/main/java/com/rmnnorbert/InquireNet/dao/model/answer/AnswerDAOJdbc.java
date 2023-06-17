package com.rmnnorbert.InquireNet.dao.model.answer;

import com.rmnnorbert.InquireNet.dao.AnswerRowMapper;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        String sql = "SELECT answer.answer_id, question_id, description, created, vote," +
                " COUNT(reply_id) as numberOfReply" +
                " from answer  " +
                "LEFT JOIN reply r on answer.answer_id = r.answer_id" +
                " GROUP BY answer.answer_id";
        return jdbcTemplate.query(sql, new AnswerRowMapper());
    }

    @Override
    public Optional<Answer> findAnswerById(int id) {
        String sql = "SELECT answer.answer_id,answer.question_id, answer.description, answer.created, answer.vote, COUNT(reply_id) as numberOfReply " +
                " FROM answer " +
                "    LEFT JOIN reply r on answer.answer_id = r.answer_id " +
                " WHERE answer.answer_id = ? " +
                " GROUP BY answer.answer_id ";
        return jdbcTemplate.query(sql, new AnswerRowMapper(),id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(int id) {
        String sql = "SELECT answer.answer_id,answer.question_id, answer.description, answer.created, answer.vote, COUNT(reply_id) as numberOfReply " +
                " FROM answer " +
                "LEFT JOIN reply r on answer.answer_id = r.answer_id " +
                "WHERE answer.question_id = ? " +
                " GROUP BY answer.answer_id ";
        return jdbcTemplate.query(sql, new AnswerRowMapper(),id);
    }


    @Override
    public int addAnswer(NewAnswerDTO newAnswerDTO) {
        String sql = "INSERT INTO answer(description,created, question_id, vote) values (?,?, ?, ?)";
        return jdbcTemplate.update(sql, newAnswerDTO.description(), LocalDateTime.now(), newAnswerDTO.questionID(), DEFAULT_VOTE);
    }

    @Override
    public boolean deleteAnswerById(int theId) {
        int delete = jdbcTemplate.update("delete from answer where answer_id = ?", theId);
        return delete == 1;
    }

    @Override
    public boolean deleteAnswerByQuestionId(int theId) {
        int delete = jdbcTemplate.update("delete from answer where question_id = ?", theId);
        return delete >= 1;
    }

    @Override
    public void update(AnswerDTO answerDTO, int id) {
        String sql = "UPDATE answer set description = ? WHERE question_id =" + id;
        jdbcTemplate.update(sql, answerDTO.getDescription());
    }

    @Override
    public void changeVote(String vote, int id) {
        String sql = "UPDATE answer set vote = ? WHERE question_id =" + id;
        jdbcTemplate.update(sql, vote);
    }
}
