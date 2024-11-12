package org.mithwick93.accountable.controller;

import org.mithwick93.accountable.controller.dto.response.UserResponse;
import org.mithwick93.accountable.controller.mapper.UserMapper;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<User> users = userService.listUsers();
        List<UserResponse> userResponses = userMapper.toUserResponses(users);
        return ResponseEntity.ok(userResponses);
    }
}
