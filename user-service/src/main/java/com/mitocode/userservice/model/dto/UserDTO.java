package com.mitocode.userservice.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String[] roles;
    private Integer port;

}