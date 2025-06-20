package org.mithwick93.accountable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.LoginRequest;
import org.mithwick93.accountable.controller.dto.request.RegistrationRequest;
import org.mithwick93.accountable.controller.dto.request.TokenRefreshRequest;
import org.mithwick93.accountable.controller.dto.request.VerifyEmailRequest;
import org.mithwick93.accountable.controller.dto.response.LoginResponse;
import org.mithwick93.accountable.controller.dto.response.MessageResponse;
import org.mithwick93.accountable.controller.dto.response.TokenRefreshResponse;
import org.mithwick93.accountable.controller.mapper.AuthMapper;
import org.mithwick93.accountable.model.RefreshToken;
import org.mithwick93.accountable.model.User;
import org.mithwick93.accountable.service.RefreshTokenService;
import org.mithwick93.accountable.service.UserService;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final AuthMapper authMapper;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User registerUser = authMapper.toUser(registrationRequest);
        userService.create(registerUser);
        return ResponseEntity.ok(MessageResponse.of("User registered successfully!"));
    }

    @PostMapping("/verify-email")
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<MessageResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyEmailRequest) {
        userService.validateEmail(verifyEmailRequest.token());
        return ResponseEntity.ok(MessageResponse.of("Email validated successfully!"));
    }

    @PostMapping("/login")
    @Transactional(rollbackFor = {Throwable.class})
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.username(), loginRequest.password());
        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return ResponseEntity.ok(LoginResponse.of(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        int userId = jwtUtil.getAuthenticatedUserId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(MessageResponse.of("Log out successful!"));
    }

    @PostMapping("/refresh-token")
    @Transactional(rollbackFor = {Throwable.class})
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.refreshToken();
        User refreshUser = refreshTokenService.refreshToken(requestRefreshToken);
        String accessToken = jwtUtil.generateToken(refreshUser);
        return ResponseEntity.ok(TokenRefreshResponse.of(accessToken, requestRefreshToken));
    }

}
