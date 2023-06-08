package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserDaoJdbc userDAO;

    @Autowired
    public UserService(UserDaoJdbc userDAO) {
        this.userDAO = userDAO;
    }


    public List<UserDTO> getAllUser() {
        return userDAO.getAllUser()
                .stream()
                .map(UserDTO::of)
                .toList();
    }

    public Optional<User> findUserById(int id) {
        return userDAO.findUserByName(id);
    }

    public Optional<User> logInUser(NewUserDTO userDTO) {
        return userDAO.findUser(userDTO);
    }
    public boolean deleteUserById(int id) {
        return userDAO.deleteUserById(id);
    }

    public int addUser(NewUserDTO userDTO) {
        return userDAO.addUser(userDTO);
    }
}
