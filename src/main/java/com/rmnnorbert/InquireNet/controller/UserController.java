package com.rmnnorbert.InquireNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




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
        public Optional<UserDTO> getUserById(@PathVariable int id) {
            return userService.findUserById(id)
                    .map(UserDTO::of);
        }
        @CrossOrigin
        @PostMapping("/create")
        public int addNewUser(@RequestBody NewUserDTO userDTO) {
            return userService.addUser(userDTO);
        }
        @CrossOrigin
        @PutMapping("/login")
        public Optional<User> loginUser(@RequestBody NewUserDTO userDTO) {
            return userService.logInUser(userDTO);
        }
        @DeleteMapping("/{id}")
        public boolean deleteUserById(@PathVariable int id) {
            return userService.deleteUserById(id);
        }
}
