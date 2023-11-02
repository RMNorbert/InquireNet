package com.rmnnorbert.InquireNet.dao.model.chat;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.rowMapper.chat.ChatRowMapper;
import com.rmnnorbert.InquireNet.dto.chat.ChatRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ChatDAOJdbc implements ChatDao{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ChatDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Chat> getAllChatByUserId(long id) {
        String sql = "SELECT chat_id, user_id, title, role, content FROM chat WHERE user_id = ?";
        return jdbcTemplate.query(sql, new ChatRowMapper(), id);
    }

    @Override
    public Chat findChatByTitle(String title) {
        String sql = "SELECT * FROM chat WHERE title = ?";
        return jdbcTemplate.query(sql, new ChatRowMapper(), title)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Chat"));
    }

    @Override
    public boolean storeChat(ChatRegisterDTO chatDTO) {
        String sql = "INSERT INTO chat(user_id, title, role, content) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, chatDTO.userId(), chatDTO.title(),chatDTO.role(), chatDTO.content()) == 1;
    }

    @Override
    public boolean deleteChatByTitle(String title) {
        String sql = "DELETE FROM chat WHERE title = ?";
        return jdbcTemplate.update(sql, title) >= 1;
    }
}
