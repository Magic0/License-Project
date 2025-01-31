package com.mitocode.userservice.expose.web;

import com.mitocode.userservice.model.dto.UserDTO;
import com.mitocode.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v0/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllLicenses() {
        List<UserDTO> licenses = userService.getAllUser();
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/api/mitocode/user")
    public ResponseEntity<String> testPrefix() {
        return ResponseEntity.ok("Response Ok");
    }
}
