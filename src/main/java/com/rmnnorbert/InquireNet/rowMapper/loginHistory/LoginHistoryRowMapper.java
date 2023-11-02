package com.rmnnorbert.InquireNet.rowMapper.loginHistory;

import com.rmnnorbert.InquireNet.dao.model.loginHistory.LoginHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHistoryRowMapper implements RowMapper<LoginHistory> {
    @Override
    public LoginHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new LoginHistory(
                rs.getLong("history_id"),
                rs.getTimestamp("time").toLocalDateTime(),
                rs.getLong("user_id"),
                rs.getString("ip_address"),
                rs.getString("user_agent")
        );
    }
}
