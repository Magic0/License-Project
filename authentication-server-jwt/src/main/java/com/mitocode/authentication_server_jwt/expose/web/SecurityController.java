package com.mitocode.authentication_server_jwt.expose.web;

import com.mitocode.authentication_server_jwt.model.request.UserCredentials;
import com.mitocode.authentication_server_jwt.model.request.UserRegister;
import com.mitocode.authentication_server_jwt.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegister userRegister){
        log.info("Registering user: {}", "hola");
        return ResponseEntity.ok(securityService.register(userRegister));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserCredentials userCredentials){
        return ResponseEntity.ok(securityService.authenticate(userCredentials));
    }


}
