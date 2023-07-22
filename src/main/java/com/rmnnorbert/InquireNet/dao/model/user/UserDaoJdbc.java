package com.rmnnorbert.InquireNet.dao.model.user;

import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dao.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDaoJdbc implements UserDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> getAllUser() {
        String sql = "SELECT id,password, role, username, registration_date" +
                " FROM \"user\"";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User findUserById(long id) {
        String sql = "SELECT id,password, role, username, registration_date" +
                " FROM \"user\" WHERE id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(),id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    public User findUser(String username) {
        String sql = "SELECT username, id,password,role,registration_date" +
                " FROM \"user\" WHERE username = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User"));
    }
    @Override
    public ResponseEntity<String> addUser(String registrationUsername, String registrationPassword) {

        String sql = "INSERT INTO \"user\" (username, password, registration_date, role)" +
                " VALUES (?, ?, ?, ?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, registrationUsername);
            ps.setString(2, registrationPassword);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4,"USER");
            return ps;
        }, keyHolder);

        return ResponseEntity.ok().body("Created successfully");
    }

    @Override
    public boolean deleteUserById(long id) {
        int delete = jdbcTemplate.update("DELETE FROM \"user\" WHERE id = ?",id);
        return delete == 1;
    }

}
