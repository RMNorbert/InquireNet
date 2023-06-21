package com.rmnnorbert.InquireNet.dao.model.user;

import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAllUser();
    Optional<User> findUserById(long id);
    Optional<User> findUser(NewUserDTO userDTO);
    int addUser(NewUserDTO userDTO);
    boolean deleteUserById(long theId);
    void updateNumberOfQuestion(User user, long id);
    void updateNumberOfAnswers(User user, long id);
}
