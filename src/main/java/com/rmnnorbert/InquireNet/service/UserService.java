package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.user.UserDaoJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserDTO findUserById(long id) {
        return UserDTO.of(userDAO.findUserById(id));
    }
    public boolean deleteUserById(DeleteRequestDTO dto) {
        UserDTO targetUser = findUserById(dto.targetId());
        if(targetUser.id() == dto.userId()) {
            return userDAO.deleteUserById(dto.targetId());
        }
        return false;
    }

}
