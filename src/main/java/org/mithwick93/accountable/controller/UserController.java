package org.mithwick93.accountable.controller;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.response.UserResponse;
import org.mithwick93.accountable.controller.mapper.UserMapper;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.service.UserService;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<User> users = userService.listUsers(jwtUtil.getAuthenticatedUserId());
        List<UserResponse> userResponses = userMapper.toUserResponses(users);
        return ResponseEntity.ok(userResponses);
    }

}
