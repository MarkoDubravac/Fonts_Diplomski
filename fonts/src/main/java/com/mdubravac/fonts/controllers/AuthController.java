package com.mdubravac.fonts.controllers;

import com.mdubravac.fonts.config.UserAuthProvider;
import com.mdubravac.fonts.dto.AdminDto;
import com.mdubravac.fonts.dto.CredentialsDto;
import com.mdubravac.fonts.dto.ErrorDto;
import com.mdubravac.fonts.dto.SignUpDto;
import com.mdubravac.fonts.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDto credentialsDto) {
        AdminDto user = userService.login(credentialsDto);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorDto("User not found or invalid credentials"));
        }

        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<AdminDto> register(@RequestBody SignUpDto signUpDto) {
        AdminDto admin = userService.register(signUpDto);
        admin.setToken(userAuthProvider.createToken(admin));
        return ResponseEntity.created(URI.create("/admins/" + admin.getId())).body(admin);
    }
}
