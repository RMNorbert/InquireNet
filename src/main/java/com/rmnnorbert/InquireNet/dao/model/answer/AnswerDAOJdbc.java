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
    @Autowired
    public AnswerDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Answer> getAllAnswers() {
        String sql = "SELECT answer_id, question_id, description, created from answer";
        return jdbcTemplate.query(sql, new AnswerRowMapper());
    }

    @Override
    public Optional<Answer> findAnswerById(int id) {
        String sql = "SELECT * FROM answer WHERE answer_id = ?";
        return jdbcTemplate.query(sql, new AnswerRowMapper())
                .stream()
                .findFirst();
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(int id) {
        String sql = "SELECT answer_id,question_id, answer.description, answer.created " +
                " FROM answer " +
                "WHERE answer.question_id = ?";
        return jdbcTemplate.query(sql, new AnswerRowMapper(),id);
    }


    @Override
    public int addAnswer(NewAnswerDTO newAnswerDTO) {
        String sql = "INSERT INTO answer(description,created, question_id) values (?,?, ?)";
        return jdbcTemplate.update(sql, newAnswerDTO.description(), LocalDateTime.now(), newAnswerDTO.questionID());
    }

    @Override
    public boolean deleteAnswerById(int theId) {
        int delete = jdbcTemplate.update("delete from answer where answer_id = ?", theId);
        return delete == 1;
    }

    @Override
    public void update(AnswerDTO answerDTO, int id) {
        String sql = "UPDATE answer set description = ? WHERE question_id =" + id;
        jdbcTemplate.update(sql, answerDTO.getDescription());
    }
}
