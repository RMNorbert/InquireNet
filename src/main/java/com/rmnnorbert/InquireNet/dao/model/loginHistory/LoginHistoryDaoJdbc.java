package com.rmnnorbert.InquireNet.dao.model.loginHistory;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.loginHistory.LoginHistoryDTO;
import com.rmnnorbert.InquireNet.rowMapper.loginHistory.LoginHistoryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class LoginHistoryDaoJdbc implements LoginHistoryDAO{
    private final JdbcTemplate jdbcTemplate;
    public LoginHistoryDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LoginHistory> getAllLoginHistory() {
        String sql = "SELECT loginHistory.history_id, loginHistory.time, loginHistory.user_id, " +
                "loginHistory.ip_address, loginHistory.user_agent FROM loginHistory";
        return jdbcTemplate.query(sql, new LoginHistoryRowMapper());
    }

    @Override
    public LoginHistory findLoginHistoryByLoginHistoryId(long id) {
        String sql = "SELECT loginHistory.history_id, loginHistory.time, loginHistory.user_id, " +
                "loginHistory.ip_address, loginHistory.user_agent FROM loginHistory" +
                " WHERE loginHistory.hiroty_id = ?";
        return jdbcTemplate.query(sql, new LoginHistoryRowMapper(), id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("History"));
    }

    @Override
    public List<LoginHistory> getAllLoginHistoryByUserId(long id) {
        String sql = "SELECT loginHistory.history_id, loginHistory.time, loginHistory.user_id, " +
                "loginHistory.ip_address, loginHistory.user_agent FROM loginHistory" +
                " WHERE loginHistory.user_id = ?";
        return jdbcTemplate.query(sql, new LoginHistoryRowMapper(), id);
    }

    @Override
    public boolean addLoginHistory(LoginHistoryDTO dto) {
        String sql = "INSERT INTO loginHistory(time,user_id, ip_address, user_agent) VALUES ( ?, ?, ?, ?) ";
        return jdbcTemplate.update(sql,
                dto.time(),
                dto.user_id(),
                dto.ip_address(),
                dto.user_agent()) == 1;
    }

    @Override
    public boolean deleteLoginHistoryById(long theId) {
        String sql = "DELETE FROM loginHistory WHERE history_id = ?";
        return jdbcTemplate.update(sql, theId) == 1;
    }

    @Override
    public boolean deleteLoginHistoryByUserId(long theId) {
        String sql = "DELETE FROM loginHistory WHERE user_id = ?";
        return jdbcTemplate.update(sql, theId) > 0;
    }
}
