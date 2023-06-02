package com.vima.gateway.controller;

import com.vima.gateway.auth.AuthenticationRequest;
import com.vima.gateway.auth.AuthenticationResponse;
import com.vima.gateway.auth.RegistrationHttpRequest;
import com.vima.gateway.dto.user.DeleteUserHttpRequest;
import com.vima.gateway.dto.user.DeleteUserHttpResponse;
import com.vima.gateway.dto.user.EditUserHttpRequest;
import com.vima.gateway.dto.user.EditUserHttpResponse;
import com.vima.gateway.security.jwt.JwtService;
import com.vima.gateway.service.AuthenticationService;
import communication.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            var user = authenticationService.loadUserByUsername(request.getUsername());
            var jwtToken = jwtService.generateToken(user);
            var authResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
            return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegistrationHttpRequest request) {
        RegistrationResponse res = authenticationService.register(request);
        return res.getMessage();
    }

    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {

        return ResponseEntity.ok(new BCryptPasswordEncoder().encode("123.Auth"));
    }

    @GetMapping("/check")
    public ResponseEntity check() {
        return ResponseEntity.ok("Ok");
    }

    @PatchMapping("/edit")
    public String edit(@RequestBody EditUserHttpRequest request){
        EditUserHttpResponse res = authenticationService.edit(request);
        return res.getMessage();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody DeleteUserHttpRequest request){
        DeleteUserHttpResponse res = authenticationService.delete(request);
        return res.getMessage();
    }

}
