package com.mitocode.authentication_server_jwt.model.request;

public record UserCredentials(
        String username,
        String password
) {
}
