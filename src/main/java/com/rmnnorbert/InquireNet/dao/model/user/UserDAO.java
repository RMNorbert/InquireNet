package com.rmnnorbert.InquireNet.dao.model.user;

import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAllUser();
    Optional<User> findUserById(int id);
    Optional<User> findUser(NewUserDTO userDTO);
    int addUser(NewUserDTO userDTO);
    boolean deleteUserById(int theId);
    void updateNumberOfQuestion(User user, int id);
    void updateNumberOfAnswers(User user, int id);
}
