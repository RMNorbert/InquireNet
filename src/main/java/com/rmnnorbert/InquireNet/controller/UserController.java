package com.rmnnorbert.InquireNet.controller;

import com.rmnnorbert.InquireNet.dao.model.user.User;
import com.rmnnorbert.InquireNet.dto.user.NewUserDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import com.rmnnorbert.InquireNet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
        private final UserService userService;
        @Autowired
        public UserController(UserService userService) {this.userService = userService;}
        @GetMapping("/all")
        public List<UserDTO> getAllUsers() {
            return userService.getAllUser();
        }
        @GetMapping("/{id}")
        public Optional<UserDTO> getUserById(@PathVariable long id) {
            return userService.findUserById(id);
        }
        @PostMapping("/create")
        public int addNewUser(@RequestBody NewUserDTO userDTO) {
            return userService.addUser(userDTO);
        }
        @PutMapping("/login")
        public Optional<User> loginUser(@RequestBody NewUserDTO userDTO) {
            return userService.logInUser(userDTO);
        }
        @DeleteMapping("/{id}")
        public boolean deleteUserById(@PathVariable long id) {
            return userService.deleteUserById(id);
        }
}
