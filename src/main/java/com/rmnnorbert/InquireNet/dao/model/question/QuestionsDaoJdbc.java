package com.rmnnorbert.InquireNet.dao.model.question;

import com.rmnnorbert.InquireNet.dao.QuestionRowMapper;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionsDaoJdbc implements QuestionDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public QuestionsDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Question> getAllQuestion() {
        String sql = "SELECT question.question_id, user_id, title, question.description, question.created," +
                " COUNT(answer_id) AS numberOfAnswers" +
                " FROM question" +
                " LEFT JOIN answer a ON question.question_id = a.question_id" +
                " GROUP BY question.question_id";

        return jdbcTemplate.query(sql, new QuestionRowMapper());
    }

    @Override
    public Question findQuestionById(long id) {
        String sql = "SELECT question.question_id,user_id, title, question.description, question.created, COUNT(answer_id) AS numberOfAnswers" +
                " FROM question" +
                " LEFT JOIN answer a ON question.question_id = a.question_id " +
                " WHERE question.question_id = ?" +
                " GROUP BY question.question_id";

        return jdbcTemplate.query(sql, new QuestionRowMapper(), id).stream().findFirst().orElseThrow(() -> new NotFoundException(" Question"));
    }
    @Override
    public List<Question> getAllQuestionByUserID(long userID) {
        String sql = "SELECT question_id, user_id, title, description, created FROM question WHERE user_id = ?";
        return jdbcTemplate.query(sql, new QuestionRowMapper(),userID);
    }
    @Override
    public int addQuestion(NewQuestionDTO newQuestionDTO) {
        if(newQuestionDTO.userID() != 0) {
            String sql = "INSERT INTO question(user_id,title,description,created) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(sql, newQuestionDTO.userID(), newQuestionDTO.title(), newQuestionDTO.description(), LocalDateTime.now());
        }
        return -1;
    }

    @Override
    public boolean deleteQuestionById(long questionId) {
        int delete = jdbcTemplate.update("DELETE FROM question WHERE question_id = ?", questionId);
        return delete == 1;
    }

    @Override
    public void update(QuestionDTO questionDTO, long id) {
        String sql = "UPDATE question SET title = ? , description = ? WHERE question_id =" + id;
        jdbcTemplate.update(sql, questionDTO.title(), questionDTO.description());
    }
    @Override
    public long findLastQuestionId() {
        int noQuestionResponse = 0;
        String sql = "SELECT MAX(question_id) FROM question";
        Optional<Question> question = jdbcTemplate.query(sql,new QuestionRowMapper()).stream().findFirst();
        return question.isPresent() ? question.get().getQuestion_id() : noQuestionResponse;
    }
}
