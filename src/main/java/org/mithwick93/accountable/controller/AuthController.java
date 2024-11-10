package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import org.mithwick93.accountable.controller.dto.request.LoginRequest;
import org.mithwick93.accountable.controller.dto.request.RegistrationRequest;
import org.mithwick93.accountable.controller.dto.response.LoginResponse;
import org.mithwick93.accountable.controller.dto.response.RegistrationResponse;
import org.mithwick93.accountable.controller.mapper.AuthMapper;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthMapper authMapper;

    @Autowired
    public AuthController(UserService userService, AuthMapper authMapper) {
        this.userService = userService;
        this.authMapper = authMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User registerUser = authMapper.toUser(registrationRequest);
        String accessToken = userService.createUser(registerUser);
        return ResponseEntity.ok(RegistrationResponse.of(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String accessToken = userService.authenticateUser(loginRequest.username(), loginRequest.password());
        return ResponseEntity.ok(LoginResponse.of(accessToken));
    }
}
