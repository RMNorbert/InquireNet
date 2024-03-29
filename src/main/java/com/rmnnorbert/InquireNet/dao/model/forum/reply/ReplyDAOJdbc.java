package com.rmnnorbert.InquireNet.dao.model.forum.reply;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.rowMapper.forum.ReplyRowMapper;
import com.rmnnorbert.InquireNet.dto.forum.reply.NewReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class ReplyDAOJdbc implements ReplyDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ReplyDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reply> getAllReply() {
        String sql = "SELECT reply_id, user_id, answer_id, description, created FROM reply";
        return jdbcTemplate.query(sql, new ReplyRowMapper());
    }

    @Override
    public Reply findReplyById(long id) {
        String sql = "SELECT * FROM reply WHERE reply_id = ?";
        return jdbcTemplate.query(sql, new ReplyRowMapper(), id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Reply"));
    }

    @Override
    public List<Reply> getAllReplyByAnswerId(long id) {
        String sql = "SELECT reply_id , user_id, answer_id, reply.description, reply.created" +
                " FROM reply" +
                " WHERE reply.answer_id = ?";
        return jdbcTemplate.query(sql, new ReplyRowMapper(),id);
    }


    @Override
    public boolean addReply(NewReplyDTO replyDTO) {
        String sql = "INSERT INTO reply(description,created, answer_id, user_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                replyDTO.description(),
                LocalDateTime.now(),
                replyDTO.answerId(),
                replyDTO.userId()) == 1;
    }

    @Override
    public boolean deleteReplyById(long theId) {
        int delete = jdbcTemplate.update("DELETE FROM reply WHERE reply_id = ?", theId);
        return delete == 1;
    }

    @Override
    public boolean deleteReplyByAnswerId(long theId) {
        int delete = jdbcTemplate.update("DELETE FROM reply WHERE answer_id = ?", theId);
        return delete >= 1;
    }

    @Override
    public boolean update(Reply replyUpdate) {
        String sql = "UPDATE reply set description = ? WHERE reply_id =" + replyUpdate.reply_id();
        return jdbcTemplate.update(sql, replyUpdate.description()) > 0;
    }
}
