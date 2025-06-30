package com.purrComplexity.TrabajoYa.Controller.Security;

import com.purrComplexity.TrabajoYa.User.AuthenticationService;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.User.dto.JwtAuthenticationResponse;
import com.purrComplexity.TrabajoYa.User.dto.SigninRequest;
import com.purrComplexity.TrabajoYa.User.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

}
