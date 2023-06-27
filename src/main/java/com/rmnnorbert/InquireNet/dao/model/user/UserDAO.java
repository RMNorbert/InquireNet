package com.rmnnorbert.InquireNet.dao.model.user;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserDAO {
    List<User> getAllUser();
    User findUserById(long id);
    User findUser(String username);
    ResponseEntity<String> addUser(String username, String password);
    boolean deleteUserById(long id);
}
