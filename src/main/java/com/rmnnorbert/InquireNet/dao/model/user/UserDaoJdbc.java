package com.rmnnorbert.InquireNet.dao.model.user;

import com.rmnnorbert.InquireNet.dao.UserRowMapper;
import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Repository
public class UserDaoJdbc implements UserDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> getAllUser() {
        String sql = "SELECT id,password, status, name, registration_date,number_of_questions,number_of_answers" +
                " FROM \"user\"";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> findUserById(long id) {
        String sql = "SELECT id,password, status,name, registration_date, number_of_questions, number_of_answers" +
                " FROM \"user\" WHERE id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(),id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findUser(NewUserDTO newUserDTO) {
        String sql = "SELECT name, id,password,status,registration_date, number_of_questions, number_of_answers" +
                " FROM \"user\" WHERE name = ? AND password = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), newUserDTO.username(),newUserDTO.password())
                .stream()
                .findFirst();
    }
    @Override
    public int addUser(NewUserDTO newUserDTO) {
        String sql = "INSERT INTO \"user\" (name, password, registration_date, number_of_questions," +
                " number_of_answers , status)" +
                " VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newUserDTO.username());
            ps.setString(2, newUserDTO.password());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setString(6,"User");
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue() + 1;
    }

    @Override
    public boolean deleteUserById(long theId) {
        int delete = jdbcTemplate.update("delete from \"user\" where id = ?",theId);
        return delete == 1;
    }

}
