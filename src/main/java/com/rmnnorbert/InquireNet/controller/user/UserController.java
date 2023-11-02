package com.rmnnorbert.InquireNet.controller.user;

import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.user.UserDTO;
import com.rmnnorbert.InquireNet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        public UserDTO getUserById(@PathVariable long id) {
            return userService.findUserById(id);
        }
        @DeleteMapping("/")
        public boolean deleteUserById(@RequestBody DeleteRequestDTO dto) {
            return userService.deleteUserById(dto);
        }
}
